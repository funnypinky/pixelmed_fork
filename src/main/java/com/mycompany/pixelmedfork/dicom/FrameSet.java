/* Copyright (c) 2001-2011, David A. Clunie DBA Pixelmed Publishing. All rights reserved. */

package com.mycompany.pixelmedfork.dicom;

import com.pixelmed.geometry.GeometryOfSlice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>A class to describe a set of frames sharing common characteristics suitable for display or analysis as an entity.</p>
 *
 * @author	dclunie
 */
class FrameSet {

	private static final String identString = "@(#) $Header: /userland/cvs/pixelmed/imgbook/com/pixelmed/dicom/FrameSet.java,v 1.10 2012/03/27 01:51:42 dclunie Exp $";
	
	private Map<AttributeTag,String> distinguishingAttributes;
	private Map<String,Map<AttributeTag,String>> perFrameAttributesIndexedBySOPInstanceUID;
	private Set<AttributeTag> perFrameAttributesPresentInAnyFrame;
	private Map<AttributeTag,String> sharedAttributes;
	private Map<AttributeTag,Integer> sharedAttributesFrameCount;
	private List<String> sopInstanceUIDsSortedByFrameOrder;
	private int numberOfFrames;
	private boolean partitioned;
	
	private Map<AttributeTag,String> mapOfUsedAttributeTagsToDictionaryKeywords = new HashMap<AttributeTag,String>();
	
	private static Set<AttributeTag> distinguishingAttributeTags = new HashSet<AttributeTag>();
	{
		distinguishingAttributeTags.add(TagFromName.PatientID);
		distinguishingAttributeTags.add(TagFromName.PatientName);

		distinguishingAttributeTags.add(TagFromName.StudyInstanceUID);

		distinguishingAttributeTags.add(TagFromName.FrameOfReferenceUID);

		distinguishingAttributeTags.add(TagFromName.Manufacturer);
		distinguishingAttributeTags.add(TagFromName.InstitutionName);
		distinguishingAttributeTags.add(TagFromName.InstitutionAddress);
		distinguishingAttributeTags.add(TagFromName.StationName);
		distinguishingAttributeTags.add(TagFromName.InstitutionalDepartmentName);
		distinguishingAttributeTags.add(TagFromName.ManufacturerModelName);
		distinguishingAttributeTags.add(TagFromName.DeviceSerialNumber);
		distinguishingAttributeTags.add(TagFromName.SoftwareVersions);
		distinguishingAttributeTags.add(TagFromName.GantryID);
		distinguishingAttributeTags.add(TagFromName.PixelPaddingValue);		// sad but true :(

		distinguishingAttributeTags.add(TagFromName.Modality);

		distinguishingAttributeTags.add(TagFromName.ImageType);
		distinguishingAttributeTags.add(TagFromName.BurnedInAnnotation);
		distinguishingAttributeTags.add(TagFromName.SOPClassUID);

		distinguishingAttributeTags.add(TagFromName.Rows);
		distinguishingAttributeTags.add(TagFromName.Columns);
		distinguishingAttributeTags.add(TagFromName.BitsStored);
		distinguishingAttributeTags.add(TagFromName.BitsAllocated);
		distinguishingAttributeTags.add(TagFromName.HighBit);
		distinguishingAttributeTags.add(TagFromName.PixelRepresentation);
		distinguishingAttributeTags.add(TagFromName.PhotometricInterpretation);
		distinguishingAttributeTags.add(TagFromName.PlanarConfiguration);
		distinguishingAttributeTags.add(TagFromName.SamplesPerPixel);
		
		//distinguishingAttributeTags.add(TagFromName.BodyPartExamined);
		
		distinguishingAttributeTags.add(TagFromName.ImageOrientationPatient);
		distinguishingAttributeTags.add(TagFromName.PixelSpacing);
		distinguishingAttributeTags.add(TagFromName.SliceThickness);
	}
	
	private static Set<AttributeTag> excludeFromGeneralPerFrameProcessingTags = new HashSet<AttributeTag>();
	{
		excludeFromGeneralPerFrameProcessingTags.addAll(distinguishingAttributeTags);
		excludeFromGeneralPerFrameProcessingTags.add(TagFromName.AcquisitionDateTime);
		excludeFromGeneralPerFrameProcessingTags.add(TagFromName.AcquisitionDate);
		excludeFromGeneralPerFrameProcessingTags.add(TagFromName.AcquisitionTime);
	}
	
	/**
	 * <p>Extract the attributes and values that are required to be common to all members of this frame set
	 * and for which different values will create distinct frame sets.</p>
	 *
	 * @param	list	a lists of DICOM attributes
	 * @return		a Map<AttributeTag,String> of the attributes and values required to be the same for membership in this frame set
	 */
	private Map<AttributeTag,String> extractDistinguishingAttributes(AttributeList list) {
		Map<AttributeTag,String> map = new TreeMap<AttributeTag,String>();	// want to keep sorted for output as toString()		
		for (AttributeTag tag : distinguishingAttributeTags) {
			map.put(tag,Attribute.getDelimitedStringValuesOrEmptyString(list,tag));
			if (mapOfUsedAttributeTagsToDictionaryKeywords.get(tag) == null) {
				mapOfUsedAttributeTagsToDictionaryKeywords.put(tag,list.getDictionary().getNameFromTag(tag));
			}
		}
		return map;
	}
	
	/**
	 * <p>Extract the attributes and values that are potentially different for all members of this frame set.</p>
	 *
	 * @param	list	a lists of DICOM attributes
	 * @return		a Map<AttributeTag,String> of the attributes and values that are potentially different for each member of this frame set
	 */
	private Map<AttributeTag,String> extractPerFrameAttributes(AttributeList list) {
		Map<AttributeTag,String> map = new TreeMap<AttributeTag,String>();	// want to keep sorted for output as toString()
		
		DicomDictionary dictionary = list.getDictionary();
		for (AttributeTag tag : list.keySet()) {
			if (! tag.isPrivate() && ! tag.isRepeatingGroup() && ! tag.isFileMetaInformationGroup() && ! tag.isGroupLength() && ! excludeFromGeneralPerFrameProcessingTags.contains(tag)) {
				Attribute a = list.get(tag);
				if (! (a instanceof SequenceAttribute)) {
					String value = a.getDelimitedStringValuesOrEmptyString();
					map.put(tag,value);
					if (!tag.equals(TagFromName.SOPInstanceUID)) {
						addToSharedAttributesIfEqualValues(tag,value);
					}
					// if there was only one frame in the frame set, SOPInstanceUID would become shared, but need to leave SOPInstanceUID in per-frame group else sorting does not work
					if (mapOfUsedAttributeTagsToDictionaryKeywords.get(tag) == null) {
						mapOfUsedAttributeTagsToDictionaryKeywords.put(tag,list.getDictionary().getNameFromTag(tag));
					}
				}
			}
		}
		
		String useAcquisitionDateTime = Attribute.getSingleStringValueOrEmptyString(list,TagFromName.AcquisitionDateTime);
		if (useAcquisitionDateTime.length() == 0) {
			useAcquisitionDateTime = Attribute.getSingleStringValueOrEmptyString(list,TagFromName.AcquisitionDate)		// could be zero lenght when time is present, which is not ideal :(
								   + Attribute.getSingleStringValueOrEmptyString(list,TagFromName.AcquisitionTime);		// assume hh is zero padded if less than 10, which should be true, but should check :(
		}
		map.put(TagFromName.AcquisitionDateTime,useAcquisitionDateTime);
		addToSharedAttributesIfEqualValues(TagFromName.AcquisitionDateTime,useAcquisitionDateTime);
		if (mapOfUsedAttributeTagsToDictionaryKeywords.get(TagFromName.AcquisitionDateTime) == null) {
			mapOfUsedAttributeTagsToDictionaryKeywords.put(TagFromName.AcquisitionDateTime,list.getDictionary().getNameFromTag(TagFromName.AcquisitionDateTime));
		}

		return map;
	}
	
	private void addToSharedAttributesIfEqualValues(AttributeTag tag,String value) {
		String sharedValue = sharedAttributes.get(tag);
		if (sharedValue == null) {
			// may be first frame, which is OK, or may not have been in previous frames, which will be detected and remove later when checking frame counts
			sharedAttributes.put(tag,value);
			sharedAttributesFrameCount.put(tag,new Integer(1));
		}
		else {
			if (sharedValue.equals(value)) {
				sharedAttributesFrameCount.put(tag,new Integer(sharedAttributesFrameCount.get(tag).intValue()+1));	// need to check later that was present for every frame
			}
			else {
				sharedAttributes.remove(tag);
			}
		}
	}
	
	private void removeSharedAttributesThatAreNotInEveryFrame() {
		Iterator<AttributeTag> i = sharedAttributes.keySet().iterator();
		while (i.hasNext()) {
			AttributeTag tag = i.next();
			int count = sharedAttributesFrameCount.get(tag).intValue();
			if (count < numberOfFrames) {
//System.err.println("FrameSet.removeSharedAttributesThatAreNotInEveryFrame(): removing "+tag+" since only present in "+count+" frames");
				i.remove();
			}
		}
	}
	
	private void removeSharedAttributesFromPerFrameAttributes() {
		for (AttributeTag tag : sharedAttributes.keySet()) {
			for (Map<AttributeTag,String> map : perFrameAttributesIndexedBySOPInstanceUID.values()) {
				map.remove(tag);
			}
		}
	}
	
	private void extractPerFrameAttributesPresentInAnyFrame() {
		perFrameAttributesPresentInAnyFrame = new TreeSet<AttributeTag>();	// want to keep sorted for output as toString()
		for (Map<AttributeTag,String> frameEntry : perFrameAttributesIndexedBySOPInstanceUID.values()) {	// traversal order doesn't matter
			perFrameAttributesPresentInAnyFrame.addAll(frameEntry.keySet());
		}
	}
	
	private class FrameSortKey implements Comparable {
	
		String sopInstanceUID;
		int seriesNumber;
		int instanceNumber;
	
		public int compareTo(Object o) {
			String oSOPInstanceUID = ((FrameSortKey)o).sopInstanceUID;
			int oInstanceNumber = ((FrameSortKey)o).instanceNumber;
			int oSeriesNumber = ((FrameSortKey)o).seriesNumber;

			return seriesNumber == oSeriesNumber
				? (
					instanceNumber == oInstanceNumber
					? (sopInstanceUID.equals(oSOPInstanceUID) ? 0 : (sopInstanceUID.hashCode() < oSOPInstanceUID.hashCode() ? -1 : 1))
					: (instanceNumber < oInstanceNumber ? -1 : 1)
				  )
				: (seriesNumber < oSeriesNumber ? -1 : 1)
				;
		}

		public boolean equals(Object o) {
			return sopInstanceUID.equals(((FrameSortKey)o).sopInstanceUID);
		}

		public int hashCode() {
			return sopInstanceUID.hashCode();
		}
		
		FrameSortKey(Map<AttributeTag,String> map) {
			sopInstanceUID = map.get(TagFromName.SOPInstanceUID);
			
			seriesNumber = -1;
			String seriesNumberAsString = map.get(TagFromName.SeriesNumber);
			if (seriesNumberAsString != null) {
				try {
					seriesNumber = Integer.parseInt(seriesNumberAsString);
				}
				catch (NumberFormatException e) {
					e.printStackTrace(System.err);
				}
			}
			
			instanceNumber = -1;
			String instanceNumberAsString = map.get(TagFromName.InstanceNumber);
			if (instanceNumberAsString != null) {
				try {
					instanceNumber = Integer.parseInt(instanceNumberAsString);
				}
				catch (NumberFormatException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	private void extractFrameSortOrderFromPerFrameAttributes() {
		SortedSet<FrameSortKey> frameSortOrder = new TreeSet<FrameSortKey>();
		for (Map<AttributeTag,String> map : perFrameAttributesIndexedBySOPInstanceUID.values()) {
			frameSortOrder.add(new FrameSortKey(map));
		}

		sopInstanceUIDsSortedByFrameOrder = new ArrayList(frameSortOrder.size());
		for (FrameSortKey frame : frameSortOrder) {
			sopInstanceUIDsSortedByFrameOrder.add(frame.sopInstanceUID);
		}
	}
	
	/**
	 * <p>Partition the FrameSet into shared and per-frame attributes.</p>
	 *
	 * <p>Must be called before the shared and per-frame attribute sets are used.</p>
	 *
	 * <p>Automatically called when toString() method is invoked.</p>
	 *
	 */
	public void partitionPerFrameIntoSharedAttributes() {
		if (!partitioned) {
			removeSharedAttributesThatAreNotInEveryFrame();
			removeSharedAttributesFromPerFrameAttributes();
			extractPerFrameAttributesPresentInAnyFrame();
			extractFrameSortOrderFromPerFrameAttributes();
			partitioned = true;
		}
	}
		
	/**
	 * <p>Check to see if a single frame object is a potential member of the current frame set.</p>
	 *
	 * @param	list	a lists of DICOM attributes for the object to be checked
	 * @return			true if the attribute list matches the criteria for membership in this frame set
	 */
	boolean eligible(AttributeList list) {
		Map<AttributeTag,String> tryMap = extractDistinguishingAttributes(list);
		boolean isEligible =  tryMap.equals(distinguishingAttributes);
//System.err.println("FrameSet.eligible(): "+isEligible);
		return isEligible;
	}
	
	/**
	 * <p>Insert the single frame object into the current frame set.</p>
	 *
	 * <p>It is assumed that the object has already been determined to be eligible.</p>
	 *
	 * @param		list			a lists of DICOM attributes for the object to be inserted
	 * @exception	DicomException	if no SOP Instance UID
	 */
	void insert(AttributeList list) throws DicomException {
		++numberOfFrames;
		String sopInstanceUID = Attribute.getSingleStringValueOrEmptyString(list,TagFromName.SOPInstanceUID);
		if (sopInstanceUID.length() > 0) {
			Map<AttributeTag,String> perFrameAttributesForThisInstance = extractPerFrameAttributes(list);
			perFrameAttributesIndexedBySOPInstanceUID.put(sopInstanceUID,perFrameAttributesForThisInstance);
		}
		else {
			throw new DicomException("Missing SOP Instance UID");
		}
		partitioned = false;
	}
	
	/**
	 * <p>Create a new frame set using the single frame object.</p>
	 *
	 * @param		list			a lists of DICOM attributes for the object from which the frame set is to be created
	 * @exception	DicomException	if no SOP Instance UID
	 */
	FrameSet(AttributeList list) throws DicomException {
		distinguishingAttributes = extractDistinguishingAttributes(list);
		perFrameAttributesIndexedBySOPInstanceUID = new TreeMap<String,Map<AttributeTag,String>>();
		perFrameAttributesPresentInAnyFrame = null;
		sharedAttributes = new TreeMap<AttributeTag,String>();				// want to keep sorted for output as toString()
		sharedAttributesFrameCount = new HashMap<AttributeTag,Integer>();	// count is used to (later) clean up tags that are not in every frame
		sopInstanceUIDsSortedByFrameOrder = null;
		numberOfFrames = 0;
		insert(list);
	}
	
	/**
	 * <p>Get a sorted list of the frames.</p>
	 *
	 * @return	a sorted list of SOP Instance UIDs
	 */
	List<String> getSOPInstanceUIDsSortedByFrameOrder() {
		partitionPerFrameIntoSharedAttributes();	// includes performing the sorting step
		return sopInstanceUIDsSortedByFrameOrder;
	}
	
	/**
	 * <p>Get the distinguishing AttributeTags used in this FrameSet.</p>
	 *
	 * @return	a set of distinguishing AttributeTags
	 */
	Set<AttributeTag> getDistinguishingAttributeTags() {
		partitionPerFrameIntoSharedAttributes();
		return distinguishingAttributes.keySet();
	}
	
	/**
	 * <p>Get the shared AttributeTags used in this FrameSet.</p>
	 *
	 * @return	a set of shared AttributeTags
	 */
	Set<AttributeTag> getSharedAttributeTags() {
		partitionPerFrameIntoSharedAttributes();
		return sharedAttributes.keySet();
	}
	
	/**
	 * <p>Get the per-frame varying AttributeTags used in this FrameSet.</p>
	 *
	 * <p>This is the set used in any frame (not necessarily all frames).</p>
	 *
	 * @return	a set of per-frame varying AttributeTags
	 */
	Set<AttributeTag> getPerFrameAttributeTags() {
		partitionPerFrameIntoSharedAttributes();
		return perFrameAttributesPresentInAnyFrame;
	}
	
	/**
	 * <p>Get the number of frames in this FrameSet.</p>
	 *
	 * @return	the number of frames in this FrameSet
	 */
	int size() {
		partitionPerFrameIntoSharedAttributes();
		return sopInstanceUIDsSortedByFrameOrder == null ? 0 : sopInstanceUIDsSortedByFrameOrder.size();
	}

	/**
	 * <p>Return a String representing a Map.Entry's value.</p>
	 *
	 * @param	entry	a key-value pair from a Map
	 * @return	a string representation of the value of this object
	 */
	private String toString(Map.Entry<AttributeTag,String> entry) {
		StringBuffer strbuf = new StringBuffer();
		AttributeTag tag = entry.getKey();
		strbuf.append(tag.toString());
		strbuf.append(" ");
		strbuf.append(mapOfUsedAttributeTagsToDictionaryKeywords.get(tag));
		strbuf.append(" = ");
		strbuf.append(entry.getValue());
		return strbuf.toString();
	}
	
	/**
	 * <p>Return a String representing this object's value.</p>
	 *
	 * @return	a string representation of the value of this object
	 */
	public String toString() {
		partitionPerFrameIntoSharedAttributes();
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("\tNumber of frames: ");
		strbuf.append(numberOfFrames);
		strbuf.append("\n");
		if (distinguishingAttributes != null) {
			strbuf.append("\tDistinguishing:\n");
			Set<Map.Entry<AttributeTag,String>> set = distinguishingAttributes.entrySet();
			for (Map.Entry<AttributeTag,String> entry : set) {
				strbuf.append("\t\t");
				strbuf.append(toString(entry));
				strbuf.append("\n");
			}
		}
		strbuf.append("\tShared:\n");
		if (sharedAttributes != null) {
			Set<Map.Entry<AttributeTag,String>> set = sharedAttributes.entrySet();
			for (Map.Entry<AttributeTag,String> entry : set) {
				strbuf.append("\t\t\t");
				strbuf.append(toString(entry));
				strbuf.append("\n");
			}
		}
		
		strbuf.append("\tPer-Frame:\n");
		if (perFrameAttributesPresentInAnyFrame != null) {
			for (AttributeTag tag : perFrameAttributesPresentInAnyFrame) {
				strbuf.append("\t\t");
				strbuf.append(tag);
				strbuf.append(" ");
				strbuf.append(AttributeList.getDictionary().getNameFromTag(tag));
				strbuf.append("\n");
			}
		}
		
		if (perFrameAttributesIndexedBySOPInstanceUID != null) {
			int j = 0;
			for (String sopInstanceUID : sopInstanceUIDsSortedByFrameOrder) {
			//for (Map<AttributeTag,String> map : perFrameAttributesIndexedBySOPInstanceUID.values()) {
//System.err.println("FrameSet.toString(): sopInstanceUID = "+sopInstanceUID);
				if (sopInstanceUID != null) {
					Map<AttributeTag,String> map = perFrameAttributesIndexedBySOPInstanceUID.get(sopInstanceUID);
					strbuf.append("\tFrame [");
					strbuf.append(Integer.toString(j));
					strbuf.append("]:\n");
					if (map != null) {
						Set<Map.Entry<AttributeTag,String>> set = map.entrySet();
						for (Map.Entry<AttributeTag,String> entry : set) {
							strbuf.append("\t\t\t");
							strbuf.append(toString(entry));
							strbuf.append("\n");
						}
					}
					++j;
				}
			}
		}
		if (sopInstanceUIDsSortedByFrameOrder != null) {
			strbuf.append("\tFrame order:\n");
			int j = 0;
			for (String sopInstanceUID : sopInstanceUIDsSortedByFrameOrder) {
				strbuf.append("\t\tFrame [");
				strbuf.append(Integer.toString(j));
				strbuf.append("]: ");
				strbuf.append(sopInstanceUID);
				strbuf.append("\n");
				++j;
			}
		}
		return strbuf.toString();
	}
}

