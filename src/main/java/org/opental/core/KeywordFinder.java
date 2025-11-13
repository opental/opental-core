package org.opental.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.opental.core.annotations.Keyword;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finder for keywords in our classpath
 */
public class KeywordFinder {

	private static final Logger logger = LoggerFactory.getLogger(KeywordFinder.class);

	private static final KeywordFinder finder = new KeywordFinder();

	/**
	 * common package scope (new)
	 */
	private static final String commonCommandPackage = "org.opental";

	/**
	 * storage for all keywords found in classpath
	 */
	private List<KeywordEntry> allCustomKeywords = new ArrayList<>();

	/**
	 * default
	 */
	public KeywordFinder() {
		logger.debug("Keywordfinder created, classpath not scanned yet. Use scan before usage.");
	}

	/**
	 * get the instance of keywordfinder
	 * 
	 * @return the instance
	 */
	public static KeywordFinder getinstance() {
		return finder;
	}

	/**
	 * Scan will clear the keyword storage and make a fresh scan.
	 */
	public void scan() {
		allCustomKeywords.clear();

		Reflections reflection = new Reflections(
				new ConfigurationBuilder()
						.addUrls(ClasspathHelper.forPackage(KeywordFinder.commonCommandPackage))
				);
		
		Set<Class<?>> aT = reflection.getTypesAnnotatedWith(Keyword.class);
		for (Class<?> oneType : aT) {
			logger.info(oneType.getName());
		}
		
		Set<Class<?>> clKeywords = reflection.getTypesAnnotatedWith(Keyword.class);

		for (Class<?> aKeywordClass : clKeywords) {
			Keyword annotation = aKeywordClass.getAnnotation(Keyword.class);
			if (annotation != null) {
				KeywordEntry newEntry = new KeywordEntry(annotation.module(), annotation.command(),
						annotation.hintTarget(), annotation.hintValue());
				allCustomKeywords.add(newEntry);
			}
		}

		logger.info("{} custom keywords found and added to the storage.", allCustomKeywords.size());
	}

	/**
	 * Returns all custom keywords, found by the annotation scanner. Needs a scan
	 * before.
	 * 
	 * @return list of all keywords defined
	 */
	public List<KeywordEntry> getKeywords() {
		return this.allCustomKeywords;
	}

	/**
	 * Returns all custom keywords, filtered by module name. Needs a scan before.
	 * 
	 * @param aModule a module to search for
	 * @return List of keywords
	 */
	public List<KeywordEntry> getKeywordsByModule(String aModule) {
		List<KeywordEntry> moduleKeywords = new ArrayList<>();
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule)) {
				moduleKeywords.add(aKeyword);
			}
		}
		logger.info("{} custom keywords for specific module found out of all: {}", moduleKeywords.size(), this.allCustomKeywords.size());
		return moduleKeywords;
	}

	/**
	 * check if a command exist in a given module
	 * @param aModule adapter module
	 * @param aCmd a command
	 * @return true, if command exists
	 */
	public boolean isKeywordExisting(String aModule, String aCmd) {
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule) && (aKeyword.getCommand().equalsIgnoreCase(aCmd))) {
					return true;
			}
		}
		return false;
	}

	/**
	 * check if target is enabled by hint pattern
	 * @param aModule adapter module
	 * @param aCmd a command
	 * @return true, if target is enabled
	 */
	public boolean isTargetEnabled(String aModule, String aCmd) {
		boolean isEnabled = true;
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule) && (aKeyword.getCommand().equalsIgnoreCase(aCmd) && aKeyword.getHintTarget().isEmpty())) {
				logger.info("Disable target field for: {}", aCmd);	
				isEnabled = false;
			}
		}
		return isEnabled;
	}

	/**
	 * check if value is enabled by hint pattern
	 * @param aModule adapter module
	 * @param aCmd a command
	 * @return true, if value is enabled
	 */
	public boolean isValueEnabled(String aModule, String aCmd) {
		boolean isEnabled = true;
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule) && (aKeyword.getCommand().equalsIgnoreCase(aCmd) && aKeyword.getHintValue().isEmpty())) {
				logger.info("Disable value field for: {}", aCmd);	
				isEnabled = false;
			}
		}
		return isEnabled;
	}

	/**
	 * Get the given hint for keyword target
	 * @param aModule adapter module
	 * @param aCmd a command
	 * @return hint pattern
	 */
	public String getTargetHint(String aModule, String aCmd) {
		String hntTarget = "";
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule) && (aKeyword.getCommand().equalsIgnoreCase(aCmd))) {
				logger.info("Get hint for target for keyword: {}", aCmd);	
				hntTarget = aKeyword.getHintTarget();
			}
		}
		return hntTarget;
	}
	
	/**
	 * Get the given hint pattern for keyword value 
	 * @param aModule adapter module
	 * @param aCmd a command
	 * @return hint pattern
	 */
	public String getValueHint(String aModule, String aCmd) {
		String hntValue = "";
		for (KeywordEntry aKeyword : this.allCustomKeywords) {
			if (aKeyword.getModule().contentEquals(aModule) && (aKeyword.getCommand().equalsIgnoreCase(aCmd))) {
				logger.info("Get hint for target for keyword: {}", aCmd);	
				hntValue = aKeyword.getHintValue();
			}
		}
		return hntValue;
	}
}