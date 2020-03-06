/* Copyright (c) 2001-2012, David A. Clunie DBA Pixelmed Publishing. All rights reserved. */

package com.pixelmed.dicom;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>The abstract base class of classes that implement individual DICOM SOP Classes or
 * groups of SOP Classes that behave similarly (for example the composite instance
 * Storage SOP Classes).
 * </p>
 * <p>There is no formal or separate abstraction of the DICOM concept of a Service Class per se.
 * </p>
 * <p>Also defines the UID strings that correspond to the individual SOP Classes, as well as
 * various utility methods for testing whether or not a string UID is of a particular family.
 * <p>
 *
 * @author	dclunie
 */
public abstract class SOPClass {
	private static final String identString = "@(#) $Header: /userland/cvs/pixelmed/imgbook/com/pixelmed/dicom/SOPClass.java,v 1.45 2012/03/12 14:25:32 dclunie Exp $";

	/***/
	public static final String Verification = "1.2.840.10008.1.1";
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the Verification SOP Class
	 */
	public static final boolean isVerification(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(Verification)
		);
	}

	// Private Storage ...

	public static final String PrivateGEPETRawDataStorage = "1.2.840.113619.4.30";
	public static final String PrivateGE3DModelStorage = "1.2.840.113619.4.26";
	public static final String PrivateGEeNTEGRAProtocolOrNMGenieStorage = "1.2.840.113619.4.27";
	public static final String PrivateGECollageStorage = "1.2.528.1.1001.5.1.1.1";
	
	public static final String PrivateSiemensCSANonImageStorage = "1.3.12.2.1107.5.9.1";
	public static final String PrivateFujiCRImageStorage = "1.2.392.200036.9125.1.1.2";
	
	public static final String PrivatePhilipsSpecialisedXAStorage       = "1.3.46.670589.2.3.1.1";
	public static final String PrivatePhilipsCXImageStorage             = "1.3.46.670589.2.4.1.1";
	public static final String PrivatePhilips3DPresentationStateStorage = "1.3.46.670589.2.5.1.1";
	public static final String PrivatePhilipsVolumeStorage              = "1.3.46.670589.5.0.1";
	public static final String PrivatePhilipsVolume2Storage             = "1.3.46.670589.5.0.1.1";
	public static final String PrivatePhilips3DObjectStorage            = "1.3.46.670589.5.0.2";
	public static final String PrivatePhilips3DObject2Storage           = "1.3.46.670589.5.0.2.1";
	public static final String PrivatePhilipsSurfaceStorage             = "1.3.46.670589.5.0.3";
	public static final String PrivatePhilipsSurface2Storage            = "1.3.46.670589.5.0.3.1";
	public static final String PrivatePhilipsCompositeObjectStorage     = "1.3.46.670589.5.0.4";
	public static final String PrivatePhilipsMRCardioProfileStorage     = "1.3.46.670589.5.0.7";
	public static final String PrivatePhilipsMRCardioStorage            = "1.3.46.670589.5.0.8";
	public static final String PrivatePhilipsMRCardio2Storage           = "1.3.46.670589.5.0.8.1";
	public static final String PrivatePhilipsCTSyntheticImageStorage    = "1.3.46.670589.5.0.9";
	public static final String PrivatePhilipsMRSyntheticImageStorage    = "1.3.46.670589.5.0.10";
	public static final String PrivatePhilipsMRCardioAnalysisStorage    = "1.3.46.670589.5.0.11";
	public static final String PrivatePhilipsMRCardioAnalysis2Storage   = "1.3.46.670589.5.0.11.1";
	public static final String PrivatePhilipsCXSyntheticImageStorage    = "1.3.46.670589.5.0.12";
	public static final String PrivatePhilipsPerfusionStorage           = "1.3.46.670589.5.0.13";
	public static final String PrivatePhilipsPerfusionImageStorage      = "1.3.46.670589.5.0.14";
	public static final String PrivatePhilipsMRSpectrumStorage          = "1.3.46.670589.11.0.0.12.1";
	public static final String PrivatePhilipsMRSeriesDataStorage        = "1.3.46.670589.11.0.0.12.2";
	public static final String PrivatePhilipsMRColorImageStorage        = "1.3.46.670589.11.0.0.12.3";
	public static final String PrivatePhilipsMRExamcardStorage          = "1.3.46.670589.11.0.0.12.4";
	
	public static final String PrivatePhilipsVRMLStorage                = "1.3.46.670589.2.8.1.1 ";
	public static final String PrivatePhilipsVolumeSetStorage           = "1.3.46.670589.2.11.1.1";
	public static final String PrivatePhilipsLiveRunStorage             = "1.3.46.670589.7.8.1618510092";
	public static final String PrivatePhilipsRunStorage                 = "1.3.46.670589.7.8.16185100129";
	public static final String PrivatePhilipsReconstructionStorage      = "1.3.46.670589.7.8.16185100130";
	public static final String PrivatePhilipsPrivateXRayMFStorage       = "1.3.46.670589.7.8.1618510091";
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known private non-image Storage SOP Classes
	 */
	public static final boolean isPrivateNonImageStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PrivateGEPETRawDataStorage)
		    || sopClassUID.equals(PrivateGE3DModelStorage)
		    || sopClassUID.equals(PrivateGEeNTEGRAProtocolOrNMGenieStorage)
		    || sopClassUID.equals(PrivateGECollageStorage)
		    || sopClassUID.equals(PrivateSiemensCSANonImageStorage)
		    || sopClassUID.equals(PrivatePhilipsVolumeStorage)
		    || sopClassUID.equals(PrivatePhilipsVolume2Storage)
		    || sopClassUID.equals(PrivatePhilips3DObjectStorage)
		    || sopClassUID.equals(PrivatePhilips3DObject2Storage)
		    || sopClassUID.equals(PrivatePhilipsSurfaceStorage)
		    || sopClassUID.equals(PrivatePhilipsSurface2Storage)
		    || sopClassUID.equals(PrivatePhilipsCompositeObjectStorage)
		    || sopClassUID.equals(PrivatePhilipsMRCardioProfileStorage)
		    || sopClassUID.equals(PrivatePhilipsMRCardioStorage)
		    || sopClassUID.equals(PrivatePhilipsMRCardio2Storage)
		    || sopClassUID.equals(PrivatePhilipsMRCardioAnalysisStorage)
		    || sopClassUID.equals(PrivatePhilipsMRCardioAnalysis2Storage)
		    || sopClassUID.equals(PrivatePhilipsMRSpectrumStorage)
		    || sopClassUID.equals(PrivatePhilipsMRSeriesDataStorage)
		    || sopClassUID.equals(PrivatePhilipsMRExamcardStorage)
		    || sopClassUID.equals(PrivatePhilips3DPresentationStateStorage)
		    || sopClassUID.equals(PrivatePhilipsPerfusionStorage)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known private image Storage SOP Classes
	 */
	public static final boolean isPrivateImageStorage(String sopClassUID) {
		return  sopClassUID != null && (
				sopClassUID.equals(PrivateFujiCRImageStorage)
			 || sopClassUID.equals(PrivatePhilipsCXImageStorage)
			 || sopClassUID.equals(PrivatePhilipsCTSyntheticImageStorage)
			 || sopClassUID.equals(PrivatePhilipsMRSyntheticImageStorage)
			 || sopClassUID.equals(PrivatePhilipsCXSyntheticImageStorage)
			 || sopClassUID.equals(PrivatePhilipsSpecialisedXAStorage)
			 || sopClassUID.equals(PrivatePhilipsPerfusionImageStorage)
			 || sopClassUID.equals(PrivatePhilipsMRColorImageStorage)
			 || sopClassUID.equals(PrivatePhilipsVRMLStorage)
			 || sopClassUID.equals(PrivatePhilipsVolumeSetStorage)
			 || sopClassUID.equals(PrivatePhilipsLiveRunStorage)
			 || sopClassUID.equals(PrivatePhilipsRunStorage)
			 || sopClassUID.equals(PrivatePhilipsReconstructionStorage)
			 || sopClassUID.equals(PrivatePhilipsPrivateXRayMFStorage)
		);
	}

	// DICOS ...

	public static final String DICOSCTImageStorage = "1.2.840.10008.5.1.4.1.1.501.1";
	public static final String DICOSDigitalXRayImageStorageForPresentation = "1.2.840.10008.5.1.4.1.1.501.2.1";
	public static final String DICOSDigitalXRayImageStorageForProcessing = "1.2.840.10008.5.1.4.1.1.501.2.2";
	public static final String DICOSThreatDetectionReportStorage = "1.2.840.10008.5.1.4.1.1.501.3";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known DICOS image Storage SOP Classes
	 */
	public static final boolean isDICOSImageStorage(String sopClassUID) {
		return  sopClassUID != null && (
				sopClassUID.equals(DICOSCTImageStorage)
			 || sopClassUID.equals(DICOSDigitalXRayImageStorageForPresentation)
			 || sopClassUID.equals(DICOSDigitalXRayImageStorageForProcessing)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known DICOS non-image Storage SOP Classes
	 */
	public static final boolean isDICOSNonImageStorage(String sopClassUID) {
		return  sopClassUID != null && (
				sopClassUID.equals(DICOSThreatDetectionReportStorage)
		);
	}

	// Images ...

	/***/
	public static final String ComputedRadiographyImageStorage = "1.2.840.10008.5.1.4.1.1.1";
	/***/
	public static final String DigitalXRayImageStorageForPresentation = "1.2.840.10008.5.1.4.1.1.1.1";
	/***/
	public static final String DigitalXRayImageStorageForProcessing = "1.2.840.10008.5.1.4.1.1.1.1.1";
	/***/
	public static final String DigitalMammographyXRayImageStorageForPresentation = "1.2.840.10008.5.1.4.1.1.1.2";
	/***/
	public static final String DigitalMammographyXRayImageStorageForProcessing = "1.2.840.10008.5.1.4.1.1.1.2.1";
	/***/
	public static final String DigitalIntraoralXRayImageStorageForPresentation = "1.2.840.10008.5.1.4.1.1.1.3";
	/***/
	public static final String DigitalIntraoralXRayImageStorageForProcessing = "1.2.840.10008.5.1.4.1.1.1.3.1";
	/***/
	public static final String CTImageStorage = "1.2.840.10008.5.1.4.1.1.2";
	/***/
	public static final String EnhancedCTImageStorage = "1.2.840.10008.5.1.4.1.1.2.1";
	/***/
	public static final String UltrasoundMultiframeImageStorageRetired = "1.2.840.10008.5.1.4.1.1.3";
	/***/
	public static final String UltrasoundMultiframeImageStorage = "1.2.840.10008.5.1.4.1.1.3.1";
	/***/
	public static final String MRImageStorage = "1.2.840.10008.5.1.4.1.1.4";
	/***/
	public static final String EnhancedMRImageStorage = "1.2.840.10008.5.1.4.1.1.4.1";
	/***/
	public static final String EnhancedMRColorImageStorage = "1.2.840.10008.5.1.4.1.1.4.3";
	/***/
	public static final String NuclearMedicineImageStorageRetired = "1.2.840.10008.5.1.4.1.1.5";
	/***/
	public static final String UltrasoundImageStorageRetired = "1.2.840.10008.5.1.4.1.1.6";
	/***/
	public static final String UltrasoundImageStorage = "1.2.840.10008.5.1.4.1.1.6.1";
	/***/
	public static final String EnhancedUSVolumeStorage = "1.2.840.10008.5.1.4.1.1.6.2";
	/***/
	public static final String SecondaryCaptureImageStorage = "1.2.840.10008.5.1.4.1.1.7";
	/***/
	public static final String MultiframeSingleBitSecondaryCaptureImageStorage = "1.2.840.10008.5.1.4.1.1.7.1";
	/***/
	public static final String MultiframeGrayscaleByteSecondaryCaptureImageStorage = "1.2.840.10008.5.1.4.1.1.7.2";
	/***/
	public static final String MultiframeGrayscaleWordSecondaryCaptureImageStorage = "1.2.840.10008.5.1.4.1.1.7.3";
	/***/
	public static final String MultiframeTrueColorSecondaryCaptureImageStorage = "1.2.840.10008.5.1.4.1.1.7.4";
	/***/
	public static final String XRayAngiographicImageStorage = "1.2.840.10008.5.1.4.1.1.12.1";
	/***/
	public static final String EnhancedXAImageStorage = "1.2.840.10008.5.1.4.1.1.12.1.1";
	/***/
	public static final String XRayRadioFlouroscopicImageStorage = "1.2.840.10008.5.1.4.1.1.12.2";
	/***/
	public static final String EnhancedXRFImageStorage = "1.2.840.10008.5.1.4.1.1.12.2.1";
	/***/
	public static final String XRayAngiographicBiplaneImageStorage = "1.2.840.10008.5.1.4.1.1.12.3";
	/***/
	public static final String XRay3DAngiographicImageStorage = "1.2.840.10008.5.1.4.1.1.13.1.1";
	/***/
	public static final String XRay3DCraniofacialImageStorage = "1.2.840.10008.5.1.4.1.1.13.1.2";
	/***/
	public static final String BreastTomosynthesisImageStorage = "1.2.840.10008.5.1.4.1.1.13.1.3";
	/***/
	public static final String NuclearMedicineImageStorage = "1.2.840.10008.5.1.4.1.1.20";
	/***/
	public static final String VisibleLightDraftImageStorage = "1.2.840.10008.5.1.4.1.1.77.1";
	/***/
	public static final String VisibleLightMultiFrameDraftImageStorage = "1.2.840.10008.5.1.4.1.1.77.2";
	/***/
	public static final String VisibleLightEndoscopicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.1";
	/***/
	public static final String VideoEndoscopicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.1.1";
	/***/
	public static final String VisibleLightMicroscopicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.2";
	/***/
	public static final String VideoMicroscopicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.2.1";
	/***/
	public static final String VisibleLightSlideCoordinatesMicroscopicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.3";
	/***/
	public static final String VisibleLightPhotographicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.4";
	/***/
	public static final String VideoPhotographicImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.4.1";
	/***/
	public static final String OphthalmicPhotography8BitImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.5.1";
	/***/
	public static final String OphthalmicPhotography16BitImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.5.2";
	/***/
	public static final String OphthalmicTomographyImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.5.4";
	/***/
	public static final String OphthalmicThicknessMapStorage = "1.2.840.10008.5.1.4.1.1.81.1";
	/***/
	public static final String VLWholeSlideMicroscopyImageStorage = "1.2.840.10008.5.1.4.1.1.77.1.6";
	/***/
	public static final String PETImageStorage = "1.2.840.10008.5.1.4.1.1.128";
	/***/
	public static final String EnhancedPETImageStorage = "1.2.840.10008.5.1.4.1.1.130";
	/***/
	public static final String RTImageStorage = "1.2.840.10008.5.1.4.1.1.481.1";
	/***/
	public static final String SegmentationStorage = "1.2.840.10008.5.1.4.1.1.66.4";


	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard or private Image Storage SOP Classes
	 */
	public static final boolean isImageStorage(String sopClassUID) {
		return isStandardImageStorage(sopClassUID)
			|| isPrivateImageStorage(sopClassUID)
			|| isDICOSImageStorage(sopClassUID)
			;
	}
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Image Storage SOP Classes
	 */
	public static final boolean isStandardImageStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(ComputedRadiographyImageStorage)
		    || sopClassUID.equals(DigitalXRayImageStorageForPresentation)
		    || sopClassUID.equals(DigitalXRayImageStorageForProcessing)
		    || sopClassUID.equals(DigitalMammographyXRayImageStorageForPresentation)
		    || sopClassUID.equals(DigitalMammographyXRayImageStorageForProcessing)
		    || sopClassUID.equals(DigitalIntraoralXRayImageStorageForPresentation)
		    || sopClassUID.equals(DigitalIntraoralXRayImageStorageForProcessing)
		    || sopClassUID.equals(CTImageStorage)
		    || sopClassUID.equals(EnhancedCTImageStorage)
		    || sopClassUID.equals(UltrasoundMultiframeImageStorageRetired)
		    || sopClassUID.equals(UltrasoundMultiframeImageStorage)
		    || sopClassUID.equals(MRImageStorage)
		    || sopClassUID.equals(EnhancedMRImageStorage)
		    || sopClassUID.equals(EnhancedMRColorImageStorage)
		    || sopClassUID.equals(NuclearMedicineImageStorageRetired)
		    || sopClassUID.equals(UltrasoundImageStorageRetired)
		    || sopClassUID.equals(UltrasoundImageStorage)
		    || sopClassUID.equals(EnhancedUSVolumeStorage)
		    || sopClassUID.equals(SecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeSingleBitSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeGrayscaleByteSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeGrayscaleWordSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeTrueColorSecondaryCaptureImageStorage)
		    || sopClassUID.equals(XRayAngiographicImageStorage)
		    || sopClassUID.equals(EnhancedXAImageStorage)
		    || sopClassUID.equals(XRayRadioFlouroscopicImageStorage)
		    || sopClassUID.equals(EnhancedXRFImageStorage)
		    || sopClassUID.equals(XRayAngiographicBiplaneImageStorage)
		    || sopClassUID.equals(XRay3DAngiographicImageStorage)
		    || sopClassUID.equals(XRay3DCraniofacialImageStorage)
		    || sopClassUID.equals(BreastTomosynthesisImageStorage)
		    || sopClassUID.equals(NuclearMedicineImageStorage)
		    || sopClassUID.equals(VisibleLightDraftImageStorage)
		    || sopClassUID.equals(VisibleLightMultiFrameDraftImageStorage)
		    || sopClassUID.equals(VisibleLightEndoscopicImageStorage)
		    || sopClassUID.equals(VisibleLightMicroscopicImageStorage)
		    || sopClassUID.equals(VisibleLightSlideCoordinatesMicroscopicImageStorage)
		    || sopClassUID.equals(VisibleLightPhotographicImageStorage)
		    || sopClassUID.equals(OphthalmicPhotography8BitImageStorage)
		    || sopClassUID.equals(OphthalmicPhotography16BitImageStorage)
			|| sopClassUID.equals(OphthalmicTomographyImageStorage)
			|| sopClassUID.equals(OphthalmicThicknessMapStorage)
			|| sopClassUID.equals(VLWholeSlideMicroscopyImageStorage)
		    || sopClassUID.equals(PETImageStorage)
		    || sopClassUID.equals(EnhancedPETImageStorage)
		    || sopClassUID.equals(RTImageStorage)
			|| sopClassUID.equals(SegmentationStorage)
		);
	}
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Multiframe Secondary Capture Image Storage SOP Classes
	 */
	public static final boolean isMultiframeSecondaryCaptureImageStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(MultiframeSingleBitSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeGrayscaleByteSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeGrayscaleWordSecondaryCaptureImageStorage)
		    || sopClassUID.equals(MultiframeTrueColorSecondaryCaptureImageStorage)
		);
	}
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Secondary Capture Image Storage SOP Classes (including Multiframe)
	 */
	public static final boolean isSecondaryCaptureImageStorage(String sopClassUID) {
		return sopClassUID != null && (
			   sopClassUID.equals(SecondaryCaptureImageStorage)
		    || isMultiframeSecondaryCaptureImageStorage(sopClassUID)
		);
	}

	// Directory ...

	/***/
	public static final String MediaStorageDirectoryStorage = "1.2.840.10008.1.3.10";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the Media Storage Directory Storage SOP Class (used for the DICOMDIR)
	 */
	public static final boolean isDirectory(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(MediaStorageDirectoryStorage)
		);
	}
	
	// Structured Report ...

	/***/
	public static final String BasicTextSRStorage = "1.2.840.10008.5.1.4.1.1.88.11";
	/***/
	public static final String EnhancedSRStorage = "1.2.840.10008.5.1.4.1.1.88.22";
	/***/
	public static final String ComprehensiveSRStorage = "1.2.840.10008.5.1.4.1.1.88.33";
	/***/
	public static final String MammographyCADSRStorage = "1.2.840.10008.5.1.4.1.1.88.50";
	/***/
	public static final String ChestCADSRStorage = "1.2.840.10008.5.1.4.1.1.88.65";
	/***/
	public static final String ProcedureLogStorage = "1.2.840.10008.5.1.4.1.1.88.40";
	/***/
	public static final String XRayRadiationDoseSRStorage = "1.2.840.10008.5.1.4.1.1.88.67";
	/***/
	public static final String ColonCADSRStorage = "1.2.840.10008.5.1.4.1.1.88.69";
	/***/
	public static final String ImplantationPlanSRStorage = "1.2.840.10008.5.1.4.1.1.88.70";
	/***/
	public static final String MacularGridThicknessAndVolumeReportStorage = "1.2.840.10008.5.1.4.1.1.79.1";
	/***/
	public static final String KeyObjectSelectionDocumentStorage = "1.2.840.10008.5.1.4.1.1.88.59";
	/***/
	public static final String SpectaclePrescriptionReportStorage = "1.2.840.10008.5.1.4.1.1.78.6";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard generic or specific Structured Report Storage SOP Classes (including Key Object)
	 */
	public static final boolean isStructuredReport(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(BasicTextSRStorage)
		    || sopClassUID.equals(EnhancedSRStorage)
		    || sopClassUID.equals(ComprehensiveSRStorage)
		    || sopClassUID.equals(MammographyCADSRStorage)
		    || sopClassUID.equals(ChestCADSRStorage)
		    || sopClassUID.equals(ProcedureLogStorage)
		    || sopClassUID.equals(XRayRadiationDoseSRStorage)
		    || sopClassUID.equals(ColonCADSRStorage)
		    || sopClassUID.equals(ImplantationPlanSRStorage)
		    || sopClassUID.equals(MacularGridThicknessAndVolumeReportStorage)
		    || sopClassUID.equals(KeyObjectSelectionDocumentStorage)
			|| sopClassUID.equals(SpectaclePrescriptionReportStorage)
		);
	}


	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the Key Object Storage SOP Class
	 */
	public static final boolean isKeyObjectSelectionDocument(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(KeyObjectSelectionDocumentStorage)
		);
	}

	// Presentation State ...

	/***/
	public static final String GrayscaleSoftcopyPresentationStateStorage = "1.2.840.10008.5.1.4.1.1.11.1";
	/***/
	public static final String ColorSoftcopyPresentationStateStorage = "1.2.840.10008.5.1.4.1.1.11.2";
	/***/
	public static final String PseudoColorSoftcopyPresentationStateStorage = "1.2.840.10008.5.1.4.1.1.11.3";
	/***/
	public static final String BlendingSoftcopyPresentationStateStorage = "1.2.840.10008.5.1.4.1.1.11.4";
	/***/
	public static final String XAXRFGrayscaleSoftcopyPresentationStateStorage = "1.2.840.10008.5.1.4.1.1.11.5";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Presentation State Storage SOP Classes (currently just the Grayscale Softcopy Presentation State Storage SOP Class)
	 */
	public static final boolean isPresentationState(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(GrayscaleSoftcopyPresentationStateStorage)
		    || sopClassUID.equals(ColorSoftcopyPresentationStateStorage)
		    || sopClassUID.equals(PseudoColorSoftcopyPresentationStateStorage)
		    || sopClassUID.equals(BlendingSoftcopyPresentationStateStorage)
		    || sopClassUID.equals(XAXRFGrayscaleSoftcopyPresentationStateStorage)
		);
	}

	// Waveforms ...

	/***/
	public static final String TwelveLeadECGStorage = "1.2.840.10008.5.1.4.1.1.9.1.1";
	/***/
	public static final String GeneralECGStorage = "1.2.840.10008.5.1.4.1.1.9.1.2";
	/***/
	public static final String AmbulatoryECGStorage = "1.2.840.10008.5.1.4.1.1.9.1.3";
	/***/
	public static final String HemodynamicWaveformStorage = "1.2.840.10008.5.1.4.1.1.9.2.1";
	/***/
	public static final String CardiacElectrophysiologyWaveformStorage = "1.2.840.10008.5.1.4.1.1.9.3.1";
	/***/
	public static final String ArterialPulseWaveformStorage = "1.2.840.10008.5.1.4.1.1.9.5.1";
	/***/
	public static final String RespiratoryWaveformStorage = "1.2.840.10008.5.1.4.1.1.9.6.1";
	/***/
	public static final String BasicVoiceStorage = "1.2.840.10008.5.1.4.1.1.9.4.1";
	/***/
	public static final String GeneralAudioWaveformStorage = "1.2.840.10008.5.1.4.1.1.9.4.2";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Waveform Storage SOP Classes
	 */
	public static final boolean isWaveform(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(TwelveLeadECGStorage)
		    || sopClassUID.equals(GeneralECGStorage)
		    || sopClassUID.equals(AmbulatoryECGStorage)
		    || sopClassUID.equals(HemodynamicWaveformStorage)
		    || sopClassUID.equals(CardiacElectrophysiologyWaveformStorage)
		    || sopClassUID.equals(ArterialPulseWaveformStorage)
		    || sopClassUID.equals(RespiratoryWaveformStorage)
		    || sopClassUID.equals(BasicVoiceStorage)
		    || sopClassUID.equals(GeneralAudioWaveformStorage)
		);
	}

	// Standalone ...

	/***/
	public static final String StandaloneOverlayStorage = "1.2.840.10008.5.1.4.1.1.8";
	/***/
	public static final String StandaloneCurveStorage = "1.2.840.10008.5.1.4.1.1.9";
	/***/
	public static final String StandaloneModalityLUTStorage = "1.2.840.10008.5.1.4.1.1.10";
	/***/
	public static final String StandaloneVOILUTStorage = "1.2.840.10008.5.1.4.1.1.11";
	/***/
	public static final String StandalonePETCurveStorage = "1.2.840.10008.5.1.4.1.1.129";
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Standalone Storage SOP Classes (overlay, curve (including PET curve), and LUTs)
	 */
	public static final boolean isStandalone(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StandaloneOverlayStorage)
		    || sopClassUID.equals(StandaloneCurveStorage)
		    || sopClassUID.equals(StandaloneModalityLUTStorage)
		    || sopClassUID.equals(StandaloneVOILUTStorage)
		    || sopClassUID.equals(StandalonePETCurveStorage)
		);
	}

	// Radiotherapy ...

	/***/
	public static final String RTDoseStorage = "1.2.840.10008.5.1.4.1.1.481.2";
	/***/
	public static final String RTStructureSetStorage = "1.2.840.10008.5.1.4.1.1.481.3";
	/***/
	public static final String RTBeamsTreatmentRecordStorage = "1.2.840.10008.5.1.4.1.1.481.4";
	/***/
	public static final String RTIonBeamsTreatmentRecordStorage = "1.2.840.10008.5.1.4.1.1.481.9";
	/***/
	public static final String RTPlanStorage = "1.2.840.10008.5.1.4.1.1.481.5";
	/***/
	public static final String RTIonPlanStorage = "1.2.840.10008.5.1.4.1.1.481.8";
	/***/
	public static final String RTBrachyTreatmentRecordStorage = "1.2.840.10008.5.1.4.1.1.481.6";
	/***/
	public static final String RTTreatmentSummaryRecordStorage = "1.2.840.10008.5.1.4.1.1.481.7";
	/***/
	public static final String RTBeamsDeliveryInstructionStorageTrial = "1.2.840.10008.5.1.4.34.1";
	/***/
	public static final String RTBeamsDeliveryInstructionStorage = "1.2.840.10008.5.1.4.34.4";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard RT non-image Storage SOP Classes (dose, structure set, plan and treatment records)
	 */
	public static final boolean isRadiotherapy(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(RTDoseStorage)
		    || sopClassUID.equals(RTStructureSetStorage)
		    || sopClassUID.equals(RTBeamsTreatmentRecordStorage)
		    || sopClassUID.equals(RTIonBeamsTreatmentRecordStorage)
		    || sopClassUID.equals(RTPlanStorage)
		    || sopClassUID.equals(RTIonPlanStorage)
		    || sopClassUID.equals(RTBrachyTreatmentRecordStorage)
		    || sopClassUID.equals(RTTreatmentSummaryRecordStorage)
		    || sopClassUID.equals(RTBeamsDeliveryInstructionStorageTrial)
		    || sopClassUID.equals(RTBeamsDeliveryInstructionStorage)
		);
	}
	
	// Spectroscopy ...
	
	/***/
	public static final String MRSpectroscopyStorage = "1.2.840.10008.5.1.4.1.1.4.2";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known standard Spectroscopy Storage SOP Classes (currently just the MR Spectroscopy Storage SOP Class)
	 */
	public static final boolean isSpectroscopy(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(MRSpectroscopyStorage)
		);
	}

	// Raw Data ...
	
	/***/
	public static final String RawDataStorage = "1.2.840.10008.5.1.4.1.1.66";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the Raw Data Storage SOP Class
	 */
	public static final boolean isRawData(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(RawDataStorage)
		);
	}

	/***/
	public static final String EncapsulatedPDFStorage = "1.2.840.10008.5.1.4.1.1.104.1";
	/***/
	public static final String EncapsulatedCDAStorage = "1.2.840.10008.5.1.4.1.1.104.2";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the Raw Data Storage SOP Class
	 */
	public static final boolean isEncapsulatedDocument(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(EncapsulatedPDFStorage)
			|| sopClassUID.equals(EncapsulatedCDAStorage)
		);
	}

	/***/
	public static final String SpatialRegistrationStorage = "1.2.840.10008.5.1.4.1.1.66.1";
	/***/
	public static final String SpatialFiducialsStorage = "1.2.840.10008.5.1.4.1.1.66.2";
	/***/
	public static final String DeformableSpatialRegistrationStorage = "1.2.840.10008.5.1.4.1.1.66.3";
	/***/
	public static final String StereometricRelationshipStorage = "1.2.840.10008.5.1.4.1.1.77.1.5.3";
	/***/
	public static final String RealWorldValueMappingStorage = "1.2.840.10008.5.1.4.1.1.67";
	/***/
	public static final String SurfaceSegmentationStorage = "1.2.840.10008.5.1.4.1.1.66.5";
	/***/
	public static final String BasicStructuredDisplayStorage = "1.2.840.10008.5.1.4.1.1.131";
	/***/
	public static final String LensometryMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.1";
	/***/
	public static final String AutorefractionMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.2";
	/***/
	public static final String KeratometryMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.3";
	/***/
	public static final String SubjectiveRefractionMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.4";
	/***/
	public static final String VisualAcuityMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.5";
	/***/
	public static final String OphthalmicAxialMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.78.7";
	/***/
	public static final String IntraocularLensCalculationsStorage = "1.2.840.10008.5.1.4.1.1.78.8";
	/***/
	public static final String OphthalmicVisualFieldStaticPerimetryMeasurementsStorage = "1.2.840.10008.5.1.4.1.1.80.1";
	
	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known refractive measurement Storage SOP Classes
	 */
	public static final boolean isOphthalmicMeasurementStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(LensometryMeasurementsStorage)
			|| sopClassUID.equals(AutorefractionMeasurementsStorage)
			|| sopClassUID.equals(KeratometryMeasurementsStorage)
			|| sopClassUID.equals(SubjectiveRefractionMeasurementsStorage)
			|| sopClassUID.equals(VisualAcuityMeasurementsStorage)
			|| sopClassUID.equals(OphthalmicAxialMeasurementsStorage)
			|| sopClassUID.equals(IntraocularLensCalculationsStorage)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known miscellaneous composite non-image Storage SOP Classes
	 */
	public static final boolean isMiscellaneousCompositeNonImageStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(SpatialRegistrationStorage)
			|| sopClassUID.equals(SpatialFiducialsStorage)
			|| sopClassUID.equals(DeformableSpatialRegistrationStorage)
			|| sopClassUID.equals(StereometricRelationshipStorage)
			|| sopClassUID.equals(RealWorldValueMappingStorage)
			|| sopClassUID.equals(SurfaceSegmentationStorage)
			|| sopClassUID.equals(BasicStructuredDisplayStorage)
			|| sopClassUID.equals(OphthalmicVisualFieldStaticPerimetryMeasurementsStorage)
		);
	}	

	/***/
	public static final String ColorPaletteStorage = "1.2.840.10008.5.1.4.39.1";
	/***/
	public static final String GenericImplantTemplateStorage = "1.2.840.10008.5.1.4.43.1";
	/***/
	public static final String ImplantAssemblyTemplateStorage = "1.2.840.10008.5.1.4.44.1";
	/***/
	public static final String ImplantTemplateGroupStorage = "1.2.840.10008.5.1.4.45.1";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known miscellaneous non-composite Storage SOP Classes
	 */
	public static final boolean isMiscellaneousNonCompositeStorage(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(ColorPaletteStorage)
			|| sopClassUID.equals(GenericImplantTemplateStorage)
			|| sopClassUID.equals(ImplantAssemblyTemplateStorage)
			|| sopClassUID.equals(ImplantTemplateGroupStorage)
		);
	}	

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known non-image Storage SOP Classes (directory, SR, presentation state, waveform, standalone, RT, spectroscopy, raw data, encapsulated document, etc. or private)
	 */
	public static final boolean isNonImageStorage(String sopClassUID) {
		return isDirectory(sopClassUID) 
		    || isStructuredReport(sopClassUID) 
		    || isPresentationState(sopClassUID)
		    || isWaveform(sopClassUID)
		    || isStandalone(sopClassUID)
		    || isRadiotherapy(sopClassUID)
		    || isSpectroscopy(sopClassUID)
		    || isRawData(sopClassUID)
			|| isEncapsulatedDocument(sopClassUID)
			|| isOphthalmicMeasurementStorage(sopClassUID)
			|| isMiscellaneousCompositeNonImageStorage(sopClassUID)
			|| isMiscellaneousNonCompositeStorage(sopClassUID)
			|| isPrivateNonImageStorage(sopClassUID)
			|| isDICOSNonImageStorage(sopClassUID)
		;
	}
	
	// Query-Retrieve SOP Classes ...

	/***/
	public static final String StudyRootQueryRetrieveInformationModelFind = "1.2.840.10008.5.1.4.1.2.2.1";
	/***/
	public static final String StudyRootQueryRetrieveInformationModelMove = "1.2.840.10008.5.1.4.1.2.2.2";
	/***/
	public static final String StudyRootQueryRetrieveInformationModelGet  = "1.2.840.10008.5.1.4.1.2.2.3";
	/***/
	public static final String PatientRootQueryRetrieveInformationModelFind = "1.2.840.10008.5.1.4.1.2.1.1";
	/***/
	public static final String PatientRootQueryRetrieveInformationModelMove = "1.2.840.10008.5.1.4.1.2.1.2";
	/***/
	public static final String PatientRootQueryRetrieveInformationModelGet  = "1.2.840.10008.5.1.4.1.2.1.3";
	/***/
	public static final String PatientStudyOnlyQueryRetrieveInformationModelFind = "1.2.840.10008.5.1.4.1.2.3.1";
	/***/
	public static final String PatientStudyOnlyQueryRetrieveInformationModelMove = "1.2.840.10008.5.1.4.1.2.3.2";
	/***/
	public static final String PatientStudyOnlyQueryRetrieveInformationModelGet  = "1.2.840.10008.5.1.4.1.2.3.3";
	/***/
	public static final String ColorPaletteInformationModelFind  = "1.2.840.10008.39.2";
	/***/
	public static final String ColorPaletteInformationModelMove  = "1.2.840.10008.39.3";
	/***/
	public static final String ColorPaletteInformationModelGet   = "1.2.840.10008.39.4";

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known composite instance query SOP Classes
	 */
	public static final boolean isCompositeInstanceQuery(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelFind)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelFind)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelFind)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the study root composite instance query SOP Class
	 */
	public static final boolean isStudyRootCompositeInstanceQuery(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelFind)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient root composite instance query SOP Class
	 */
	public static final boolean isPatientRootCompositeInstanceQuery(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientRootQueryRetrieveInformationModelFind)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient/study only composite instance query SOP Class
	 */
	public static final boolean isPatientStudyOnlyCompositeInstanceQuery(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelFind)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known composite instance retrieve move SOP Classes
	 */
	public static final boolean isCompositeInstanceRetrieveWithMove(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelMove)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the study root composite instance retrieve move SOP Class
	 */
	public static final boolean isStudyRootCompositeInstanceRetrieveWithMove(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelMove)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient root composite instance retrieve move SOP Class
	 */
	public static final boolean isPatientRootCompositeInstanceRetrieveWithMove(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientRootQueryRetrieveInformationModelMove)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient/study only composite instance retrieve move SOP Class
	 */
	public static final boolean isPatientStudyOnlyCompositeInstanceRetrieveWithMove(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelMove)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known composite instance get SOP Classes
	 */
	public static final boolean isCompositeInstanceRetrieveWithGet(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelGet)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelGet)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the study root composite instance get SOP Class
	 */
	public static final boolean isStudyRootCompositeInstanceRetrieveWithGet(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient root composite instance get SOP Class
	 */
	public static final boolean isPatientRootCompositeInstanceRetrieveWithGet(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientRootQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient/study only composite instance get SOP Class
	 */
	public static final boolean isPatientStudyOnlyCompositeInstanceRetrieveWithGet(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelGet)
		);
	}


	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches one of the known composite instance retrieve move or get SOP Classes
	 */
	public static final boolean isCompositeInstanceRetrieve(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(StudyRootQueryRetrieveInformationModelGet)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelGet)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the study root composite instance retrieve move or get SOP Classes
	 */
	public static final boolean isStudyRootCompositeInstanceRetrieve(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(StudyRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(StudyRootQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient root composite instance retrieve move or get SOP Classes
	 */
	public static final boolean isPatientRootCompositeInstanceRetrieve(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientRootQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientRootQueryRetrieveInformationModelGet)
		);
	}

	/**
	 * @param	sopClassUID	UID of the SOP Class, as a String without trailing zero padding
	 * @return			true if the UID argument matches the patient/study only composite instance retrieve move or get SOP Classes
	 */
	public static final boolean isPatientStudyOnlyCompositeInstanceRetrieve(String sopClassUID) {
		return sopClassUID != null && (
		       sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelMove)
		    || sopClassUID.equals(PatientStudyOnlyQueryRetrieveInformationModelGet)
		);
	}

	public static final String[] arrayOfQuerySOPClasses = {
		StudyRootQueryRetrieveInformationModelFind,
		PatientRootQueryRetrieveInformationModelFind,
		PatientStudyOnlyQueryRetrieveInformationModelFind,
		ColorPaletteInformationModelFind
	};
	
	public static final String[] arrayOfRetrieveWithMoveSOPClasses = {
		StudyRootQueryRetrieveInformationModelMove,
		PatientRootQueryRetrieveInformationModelMove,
		PatientStudyOnlyQueryRetrieveInformationModelMove,
		ColorPaletteInformationModelMove
	};
	
	public static final String[] arrayOfRetrieveWithGetSOPClasses = {
		StudyRootQueryRetrieveInformationModelGet,
		PatientRootQueryRetrieveInformationModelGet,
		PatientStudyOnlyQueryRetrieveInformationModelGet,
		ColorPaletteInformationModelGet
	};
	
	public static final String[] arrayOfStorageSOPClasses = {
		ComputedRadiographyImageStorage,
		DigitalXRayImageStorageForPresentation,
		DigitalXRayImageStorageForProcessing,
		DigitalMammographyXRayImageStorageForPresentation,
		DigitalMammographyXRayImageStorageForProcessing,
		DigitalIntraoralXRayImageStorageForPresentation,
		DigitalIntraoralXRayImageStorageForProcessing,
		CTImageStorage,
		EnhancedCTImageStorage,
		UltrasoundMultiframeImageStorageRetired,
		UltrasoundMultiframeImageStorage,
		MRImageStorage,
		EnhancedMRImageStorage,
		NuclearMedicineImageStorageRetired,
		UltrasoundImageStorageRetired,
		UltrasoundImageStorage,
		EnhancedUSVolumeStorage,
		SecondaryCaptureImageStorage,
		MultiframeSingleBitSecondaryCaptureImageStorage,
		MultiframeGrayscaleByteSecondaryCaptureImageStorage,
		MultiframeGrayscaleWordSecondaryCaptureImageStorage,
		MultiframeTrueColorSecondaryCaptureImageStorage,
		XRayAngiographicImageStorage,
		EnhancedXAImageStorage,
		XRayRadioFlouroscopicImageStorage,
		EnhancedXRFImageStorage,
		XRayAngiographicBiplaneImageStorage,
		XRay3DAngiographicImageStorage,
		XRay3DCraniofacialImageStorage,
		BreastTomosynthesisImageStorage,
		NuclearMedicineImageStorage,
		VisibleLightDraftImageStorage,
		VisibleLightMultiFrameDraftImageStorage,
		VisibleLightEndoscopicImageStorage,
		VideoEndoscopicImageStorage,
		VisibleLightMicroscopicImageStorage,
		VideoMicroscopicImageStorage,
		VisibleLightSlideCoordinatesMicroscopicImageStorage,
		VisibleLightPhotographicImageStorage,
		VideoPhotographicImageStorage,
		OphthalmicPhotography8BitImageStorage,
		OphthalmicPhotography16BitImageStorage,
		OphthalmicTomographyImageStorage,
		OphthalmicThicknessMapStorage,
		VLWholeSlideMicroscopyImageStorage,
		PETImageStorage,
		RTImageStorage,
		BasicTextSRStorage,
		EnhancedSRStorage,
		ComprehensiveSRStorage,
		MammographyCADSRStorage,
		ChestCADSRStorage,
		ProcedureLogStorage,
		XRayRadiationDoseSRStorage,
		ColonCADSRStorage,
		ImplantationPlanSRStorage,
		MacularGridThicknessAndVolumeReportStorage,
		KeyObjectSelectionDocumentStorage,
		GrayscaleSoftcopyPresentationStateStorage,
		ColorSoftcopyPresentationStateStorage,
		PseudoColorSoftcopyPresentationStateStorage,
		BlendingSoftcopyPresentationStateStorage,
		XAXRFGrayscaleSoftcopyPresentationStateStorage,
		TwelveLeadECGStorage,
		GeneralECGStorage,
		AmbulatoryECGStorage,
		HemodynamicWaveformStorage,
		CardiacElectrophysiologyWaveformStorage,
		ArterialPulseWaveformStorage,
		RespiratoryWaveformStorage,
		BasicVoiceStorage,
		GeneralAudioWaveformStorage,
		StandaloneOverlayStorage,
		StandaloneCurveStorage,
		StandaloneModalityLUTStorage,
		StandaloneVOILUTStorage,
		StandalonePETCurveStorage,
		RTDoseStorage,
		RTStructureSetStorage,
		RTBeamsTreatmentRecordStorage,
		RTIonBeamsTreatmentRecordStorage,
		RTPlanStorage,
		RTIonPlanStorage,
		RTBrachyTreatmentRecordStorage,
		RTTreatmentSummaryRecordStorage,
		MRSpectroscopyStorage,
		RawDataStorage,
		SpatialRegistrationStorage,
		SpatialFiducialsStorage,
		DeformableSpatialRegistrationStorage,
		StereometricRelationshipStorage,
		RealWorldValueMappingStorage,
		EncapsulatedPDFStorage,
		EncapsulatedCDAStorage,
		PrivateGEPETRawDataStorage,
		PrivateGE3DModelStorage,
		PrivateSiemensCSANonImageStorage,
		PrivateFujiCRImageStorage,
		EnhancedMRColorImageStorage,
		EnhancedPETImageStorage,
		SegmentationStorage,
		SurfaceSegmentationStorage,
		BasicStructuredDisplayStorage,
		LensometryMeasurementsStorage,
		AutorefractionMeasurementsStorage,
		KeratometryMeasurementsStorage,
		SubjectiveRefractionMeasurementsStorage,
		VisualAcuityMeasurementsStorage,
		SpectaclePrescriptionReportStorage,
		OphthalmicAxialMeasurementsStorage,
		IntraocularLensCalculationsStorage,
		OphthalmicVisualFieldStaticPerimetryMeasurementsStorage,
		ColorPaletteStorage
	};
		
	private static final Set initializeUnmodifiableSetIfNecessary(Set unmodifiableSet,String[] array) {
		if (unmodifiableSet == null) {
			Set set = new HashSet();
			for (int i=0; i<array.length; ++i) {
				set.add(array[i]);
			}
			unmodifiableSet = Collections.unmodifiableSet(set);
		}
		return unmodifiableSet;
	}
	
	private static Set setOfStorageSOPClasses = null;
	
	/**
	 * @return			an (unmodifiable) <code>Set</code> of known Storage SOP Classes
	 */
	public static final Set getSetOfStorageSOPClasses() {
		setOfStorageSOPClasses = initializeUnmodifiableSetIfNecessary(setOfStorageSOPClasses,arrayOfStorageSOPClasses);
		return setOfStorageSOPClasses;
	}
	
	private static final Map<String,String[]> plausibleStandardSOPClassUIDsForModality;
	static {
		plausibleStandardSOPClassUIDsForModality = new HashMap<String,String[]>();
		plausibleStandardSOPClassUIDsForModality.put(
			"CT",
			new String[] {
				CTImageStorage,
				EnhancedCTImageStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"MR",
			new String[] {
				MRImageStorage,
				EnhancedMRImageStorage,
				EnhancedMRColorImageStorage,
				MRSpectroscopyStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"US",
			new String[] {
				UltrasoundImageStorage,
				UltrasoundMultiframeImageStorage,
				UltrasoundImageStorageRetired,
				UltrasoundMultiframeImageStorageRetired,
				EnhancedUSVolumeStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"IVUS",
			new String[] {
				UltrasoundImageStorage,
				UltrasoundMultiframeImageStorage,
				UltrasoundImageStorageRetired,
				UltrasoundMultiframeImageStorageRetired,
				EnhancedUSVolumeStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"NM",
			new String[] {
				NuclearMedicineImageStorage,
				NuclearMedicineImageStorageRetired,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"PT",
			new String[] {
				PETImageStorage,
				EnhancedPETImageStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"XA",
			new String[] {
				XRayAngiographicImageStorage,
				EnhancedXAImageStorage,
				XRay3DAngiographicImageStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"XRF",
			new String[] {
				XRayRadioFlouroscopicImageStorage,
				EnhancedXRFImageStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"XA",
			new String[] {
				XRayAngiographicImageStorage,
				XRayAngiographicBiplaneImageStorage,
				EnhancedXAImageStorage,
				XRay3DAngiographicImageStorage,
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedPDFStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"CR",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				ComputedRadiographyImageStorage,
				DigitalXRayImageStorageForProcessing,
				DigitalXRayImageStorageForPresentation,
				DigitalIntraoralXRayImageStorageForProcessing,
				DigitalIntraoralXRayImageStorageForPresentation,
				DigitalMammographyXRayImageStorageForProcessing,
				DigitalMammographyXRayImageStorageForPresentation,
				XRay3DCraniofacialImageStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"DX",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				ComputedRadiographyImageStorage,
				DigitalXRayImageStorageForProcessing,
				DigitalXRayImageStorageForPresentation,
				DigitalIntraoralXRayImageStorageForProcessing,
				DigitalIntraoralXRayImageStorageForPresentation,
				DigitalMammographyXRayImageStorageForProcessing,
				DigitalMammographyXRayImageStorageForPresentation,
				XRay3DCraniofacialImageStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"IO",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				ComputedRadiographyImageStorage,
				DigitalXRayImageStorageForProcessing,
				DigitalXRayImageStorageForPresentation,
				DigitalIntraoralXRayImageStorageForProcessing,
				DigitalIntraoralXRayImageStorageForPresentation
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"MG",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				ComputedRadiographyImageStorage,
				DigitalXRayImageStorageForProcessing,
				DigitalXRayImageStorageForPresentation,
				DigitalMammographyXRayImageStorageForProcessing,
				DigitalMammographyXRayImageStorageForPresentation,
				BreastTomosynthesisImageStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"GM",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				VisibleLightMicroscopicImageStorage,
				VideoMicroscopicImageStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"SM",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				VisibleLightSlideCoordinatesMicroscopicImageStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"XC",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				VisibleLightPhotographicImageStorage,
				VideoPhotographicImageStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"OP",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				VisibleLightPhotographicImageStorage,
				OphthalmicPhotography8BitImageStorage,
				OphthalmicPhotography16BitImageStorage,
				EncapsulatedPDFStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"OPT",
			new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				OphthalmicTomographyImageStorage,
				EncapsulatedPDFStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"OPM",
			new String[] {
				OphthalmicThicknessMapStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"RTIMAGE",
			new String[] {
				RTImageStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"RTDOSE",
			new String[] {
				RTDoseStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"RTSTRUCT",
			new String[] {
				RTStructureSetStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"RTPLAN",
			new String[] {
				RTPlanStorage,
				RTIonPlanStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"RTRECORD",
			new String[] {
				RTTreatmentSummaryRecordStorage,
				RTIonBeamsTreatmentRecordStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"SR",
			new String[] {
				BasicTextSRStorage,
				EnhancedSRStorage,
				ComprehensiveSRStorage,
				MammographyCADSRStorage,
				ChestCADSRStorage,
				ProcedureLogStorage,
				XRayRadiationDoseSRStorage,
				ColonCADSRStorage,
				ImplantationPlanSRStorage,
				MacularGridThicknessAndVolumeReportStorage,
				KeyObjectSelectionDocumentStorage,
				SpectaclePrescriptionReportStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"REG",
			new String[] {
				SpatialRegistrationStorage,
				DeformableSpatialRegistrationStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"FID",
			new String[] {
				SpatialFiducialsStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"SMR",
			new String[] {
				StereometricRelationshipStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"RWV",
			new String[] {
				RealWorldValueMappingStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"SEG",
			new String[] {
				SegmentationStorage,
				SurfaceSegmentationStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"LEN",
			new String[] {
				LensometryMeasurementsStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"AR",
			new String[] {
				AutorefractionMeasurementsStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"KER",
			new String[] {
				KeratometryMeasurementsStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"SRF",
			new String[] {
				SubjectiveRefractionMeasurementsStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"VA",
			new String[] {
				VisualAcuityMeasurementsStorage,
				EncapsulatedPDFStorage
			});

		plausibleStandardSOPClassUIDsForModality.put(
			"AU",
			new String[] {
				BasicVoiceStorage,
				GeneralAudioWaveformStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"ECG",
			new String[] {
				TwelveLeadECGStorage,
				GeneralECGStorage,
				AmbulatoryECGStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"HD",
			new String[] {
				HemodynamicWaveformStorage,
				ArterialPulseWaveformStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"EPS",
			new String[] {
				CardiacElectrophysiologyWaveformStorage,
				EncapsulatedPDFStorage
			});
		plausibleStandardSOPClassUIDsForModality.put(
			"RESP",
			new String[] {
				RespiratoryWaveformStorage,
				EncapsulatedPDFStorage
			});
	}
	
	/**
	 * <p>Return a plausible set of SOP Classes with which to encode a specified modality.</p>
	 *
	 * <p>Useful for association negitation for a C-GET operation when the SOP Classes to be retrieved are not explicitly known.</p>
	 *
	 * <p>Includes both those SOP Classes that have mandatory fixed modality values, as well as
	 * likely secondary capture, encapsulated, raw data and more general SOP Classes that could be used.</p>
	 *
	 * @param	modality	a DICOM standard string value for Modality
	 * @return				an array of DICOM standard Storage SOP Class UIDs
	 */
	public static String[] getPlausibleStandardSOPClassUIDsForModality(String modality) {
		String[] sopClassUIDs = null;
		if (modality != null) {
			sopClassUIDs = plausibleStandardSOPClassUIDsForModality.get(modality);
		}
		if (sopClassUIDs == null || sopClassUIDs.length == 0) {
			// unrecognized, so allow any secondary capture or raw data
			sopClassUIDs = new String[] {
				SecondaryCaptureImageStorage,
				MultiframeSingleBitSecondaryCaptureImageStorage,
				MultiframeGrayscaleByteSecondaryCaptureImageStorage,
				MultiframeGrayscaleWordSecondaryCaptureImageStorage,
				MultiframeTrueColorSecondaryCaptureImageStorage,
				RawDataStorage,
				EncapsulatedCDAStorage,
				EncapsulatedPDFStorage
			};
		}
		return sopClassUIDs;
	}
}
