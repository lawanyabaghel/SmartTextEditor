package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.OptionalInt;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import org.fxmisc.richtext.PopupAlignment;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.richtext.StyledTextArea;

public class AutoSpellingTextArea extends StyledTextArea<Boolean> {

	private static final int NUM_COMPLETIONS = 6;
	private static final int NUM_SUGGESTIONS = 6;

	// track auto complete and spelling suggestion states
	private boolean autoCompleteOn = false;
	private boolean spellingOn = false;

	// boolean to see if style needs update on plain text change
	private boolean needUpdate = true;

	// matches case of user typing for auto complete and ss
	// turn off if handling caps
	private boolean matchCase = true;

	// indices which contain word, set by getWordAtIndex
	private int startIndex;
	private int endIndex;

	// current autocomplete options
	private List<String> options;

	// popup to display/select entry
	private ContextMenu entriesPopup;

	private spelling.AutoComplete ac;
	private spelling.Dictionary dic;
	private spelling.SpellingSuggest ss;

	// set up reflection for spelling suggest
	private static Method mHit;
	private static Method mGetCharacterIndex;
	private static Object styledView;

	static {
		try {
			mHit = Class.forName("org.fxmisc.richtext.skin.StyledTextAreaView").getDeclaredMethod("hit", double.class,
					double.class);
			mGetCharacterIndex = Class.forName("org.fxmisc.richtext.skin.CharacterHit")
					.getDeclaredMethod("getCharacterIndex");

		} catch (ClassNotFoundException | NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		}

		mHit.setAccessible(true);
		mGetCharacterIndex.setAccessible(true);
	}

	public AutoSpellingTextArea(spelling.AutoComplete ac, spelling.SpellingSuggest ss, spelling.Dictionary dic) {
		super(true, (textNode, correct) -> {
			// define boolean Text node style
			if (!correct) {
				textNode.setUnderline(true);
				textNode.setBackgroundFill(Color.TOMATO);
			}
		});

		// save objects passed in
		this.ac = ac;
		this.ss = ss;

		// dictionary used in spelling suggestions
		this.dic = dic;

		this.setOnMouseClicked(e -> {
			if ((e.getButton() == MouseButton.SECONDARY) && spellingOn) {
				// get StyledTextAreaView object
				styledView = getChildrenUnmodifiable().get(0);

				// get character hit on click and index
				Object chHit = invoke(mHit, styledView, e.getX(), e.getY());
				OptionalInt opInt = (OptionalInt) invoke(mGetCharacterIndex, chHit);

				// valid index clicked
				if (opInt.isPresent()) {
					int index = opInt.getAsInt();

					// check if index corresponds to misspelled word
					if (!getStyleOfChar(index)) {
						String possibleWord = getWordAtIndex(index);
						showSuggestions(possibleWord, e);
					}
				}

			}

		});

		// keep track of text changes, update spell check if needed
		this.plainTextChanges().subscribe(change -> {
			if (spellingOn && needUpdate) {
				this.setStyleSpans(0, checkSpelling());
			}
		});

		options = new ArrayList<String>();
		entriesPopup = new ContextMenu();
		setPopupWindow(entriesPopup);
		setPopupAlignment(PopupAlignment.CARET_BOTTOM);

		this.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
			if (autoCompleteOn) {
				String prefix = getWordAtIndex(newPosition.intValue());
				showCompletions(prefix);
			}

		});
	}


	private String getWordAtIndex(int pos) {
		String text = this.getText().substring(0, pos);

		int index;

		for (index = text.length() - 1; index >= 0 && !Character.isWhitespace(text.charAt(index)); index--);

		String prefix = text.substring(index + 1, text.length());
		startIndex = index + 1;

        for (index = pos; index < this.getLength() && !Character.isWhitespace(this.getText().charAt(index)); index++);

		String suffix = this.getText().substring(pos, index);
		endIndex = index;

		prefix = prefix.replaceAll("\\.", "\\.");
		suffix = suffix.replaceAll("\\.", "\\.");

		prefix = prefix + suffix;

		return prefix;
	}

	private List<CustomMenuItem> createOptions(List<String> options, boolean[] flags) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		int count = Math.min(options.size(), NUM_SUGGESTIONS);

		// add options to ContextMenu
		for (int i = 0; i < count; i++) {
			String str = options.get(i);

			if (flags != null) {
				str = convertCaseUsingFlags(str, flags);
			}

			final String result = str;

			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);

			// register event where user chooses word (click)
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent actionEvent) {
					needUpdate = false;
					replaceText(startIndex, endIndex, result);
					getWordAtIndex(startIndex);
					setStyle(startIndex, endIndex, true);
					needUpdate = true;
				}
			});

			menuItems.add(item);
		}

		return menuItems;
	}

	public StyleSpans<Boolean> checkSpelling() {
		String text = getText();
		String word;

		// keep track of end of matcher
		int lastEnd = 0;
		StyleSpansBuilder<Boolean> spansBuilder = new StyleSpansBuilder<>();

		// Pattern and Matcher to get whitespace delimited words
		Pattern pattern = Pattern.compile("[\\w'-]+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			word = matcher.group();

			boolean styleClass = dic.isWord(word);
			spansBuilder.add(true, matcher.start() - lastEnd);
			spansBuilder.add(styleClass, matcher.end() - matcher.start());
			lastEnd = matcher.end();
		}

		// set trailing characters to true
		spansBuilder.add(true, text.length() - lastEnd);

		return spansBuilder.create();

	}

	private void showSuggestions(String word, MouseEvent click) {
		List<String> suggestions = ss.suggestions(word, NUM_SUGGESTIONS);

		// boolean array for matching case of use input
		boolean[] caseFlags = null;
		if (matchCase) {
			caseFlags = getCaseFlags(word);
		}

		// references for creating menu items
		Label sLabel;
		CustomMenuItem item;

		entriesPopup.getItems().clear();

		List<CustomMenuItem> menuItems = createOptions(suggestions, caseFlags);

		// check if no suggestions found
		if (suggestions.size() == 0) {
			sLabel = new Label("No suggestions.");
			item = new CustomMenuItem(sLabel, true);
			item.setDisable(true);
			((LinkedList<CustomMenuItem>) menuItems).addFirst(item);
		}

		// add "misspelled" word to dictionary
		sLabel = new Label("Add to dictionary.");
		item = new CustomMenuItem(sLabel, true);

		// register event to add word
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// add word to both dictionaries
				dic.addWord(word);
				((spelling.Dictionary) ac).addWord(word);

				setStyle(startIndex, endIndex, true);
			}
		});

		menuItems.add(item);

		entriesPopup.getItems().addAll(menuItems);
		entriesPopup.show(getScene().getWindow(), click.getScreenX(), click.getScreenY());

	}

	private void showCompletions(String prefix) {
		// keep track of prefix
		// check if in middle of typing word
		if (!prefix.equals("")) {

			// get completion options
			options = (ac).predictCompletions(prefix, NUM_COMPLETIONS);

			// check if options found
			if (options.size() > 0) {

				// boolean array for matching case of use input
				boolean[] caseFlags = null;
				if (matchCase) {
					caseFlags = getCaseFlags(prefix);
				}

				List<CustomMenuItem> menuOptions = createOptions(options, caseFlags);

				entriesPopup.getItems().clear();
				entriesPopup.getItems().addAll(menuOptions);

				if (!entriesPopup.isShowing()) {
					entriesPopup.show(getScene().getWindow());
				}
			}
			// no options for complete
			else {
				entriesPopup.hide();
			}

		}
		// no current prefix
		else {
			entriesPopup.hide();
		}
	}

	public void setSpelling(boolean state) {
		spellingOn = state;

		if (state == true && getText().length() > 0) {
			this.setStyleSpans(0, checkSpelling());
		}
		// change all text to true/correct style
		else {
			setStyle(0, getText().length(), true);
		}
	}

	public void setAutoComplete(boolean state) {
		autoCompleteOn = state;
	}

	/**
	 * Returns a boolean array with true in position where word has an upper
	 * case letter
	 * 
	 * @param word
	 * @return boolean array for uppercase or null if none
	 */
	public boolean[] getCaseFlags(String word) {

		boolean[] flags = new boolean[word.length()];

		// for if should return array or null
		boolean anyUpperCase = false;

		for (int i = 0; i < flags.length; i++) {
			// if isUpperCase
			if (Character.isUpperCase(word.charAt(i))) {
				flags[i] = true;
				anyUpperCase = true;
			} else {
				flags[i] = false;
			}
		}

		if (anyUpperCase) {
			return flags;
		}

		return null;
	}

	/**
	 * Converts characters in word passed in which have "true" in the parallel
	 * index to flags array
	 * 
	 * @param word
	 * @param flags
	 * @return string with uppercase in true positions
	 */
	private String convertCaseUsingFlags(String word, boolean[] flags) {
		StringBuilder sb = new StringBuilder(word);

		for (int i = 0; i < flags.length && i < word.length(); i++) {
			if (flags[i]) {
				char c = sb.charAt(i);
				sb.setCharAt(i, Character.toUpperCase(c));
			}
		}

		return sb.toString();
	}

	// encapsulate Method.invoke method
	private static Object invoke(Method m, Object obj, Object... args) {
		try {
			return m.invoke(obj, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
