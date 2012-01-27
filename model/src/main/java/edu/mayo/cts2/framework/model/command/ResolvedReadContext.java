package edu.mayo.cts2.framework.model.command;

import edu.mayo.cts2.framework.model.core.LanguageReference;
import edu.mayo.cts2.framework.model.service.core.types.ActiveOrAll;

public class ResolvedReadContext {

	/**
	 * Determines whether the query only applies to only active or all entries.
	 */
	private ActiveOrAll active = ActiveOrAll.ACTIVE_ONLY;

	/**
	 * The spoken or written language that should be used for the results of the
	 * inquiry, where appropriate.
	 */
	private LanguageReference languageReference;

	/**
	 * The URI of an open change set whose contents should be included in the
	 * results of the access request. changeSetContext is only applicable in
	 * services that support the Authoring profile
	 */
	private String changeSetContextUri;

	/**
	 * The contextual date and time of the query. referenceTime is may only be
	 * present in services that support the Temporal profile.
	 */
	private java.util.Date referenceTime;

	public ActiveOrAll getActive() {
		return active;
	}

	public void setActive(ActiveOrAll active) {
		this.active = active;
	}

	public LanguageReference getLanguageReference() {
		return languageReference;
	}

	public void setLanguageReference(LanguageReference languageReference) {
		this.languageReference = languageReference;
	}

	public java.lang.String getChangeSetContextUri() {
		return changeSetContextUri;
	}

	public void setChangeSetContextUri(java.lang.String changeSetContextUri) {
		this.changeSetContextUri = changeSetContextUri;
	}

	public java.util.Date getReferenceTime() {
		return referenceTime;
	}

	public void setReferenceTime(java.util.Date referenceTime) {
		this.referenceTime = referenceTime;
	}
}