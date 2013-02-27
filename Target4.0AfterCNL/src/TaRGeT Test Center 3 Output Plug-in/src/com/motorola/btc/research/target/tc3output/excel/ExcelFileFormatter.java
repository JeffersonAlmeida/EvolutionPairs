/*
 * @(#)ExcelFileFormatter.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Jan 8, 2007    LIBkk11577   Initial creation.
 * wdt022    Jan 18, 2007   LIBkk11577   Rework after inspection (LX135556).
 * wdt022    Feb 23, 2007   LIBkk16317   Fix excel file encoding.
 * dhq348    Jul 05, 2007   LIBmm76986   Modifications according to CR.
 * dhq348    Jul 06, 2007   LIBmm76995   Modifications according to CR.
 * dhq348    Nov 21, 2007   LIBoo10574   It is now filling the blank fields with default values.
 * dhq348    Jan 22, 2008   LIBoo10574   Rework after inspection LX229632.
 * tnd783    Apr 07, 2008   LIBpp71785   Added hidden cell with test information
 * tnd783    Jul 21, 2008   LIBpp71785   Rework after inspection LX285039. 
 * tnd783 	 Sep 08, 2008	LIBqq51204   Changes made to consider the initial ID
 * jrm687    Dec 18, 2008   LIBnn34008	 Modifications to add document revision history.
 * cvn768    Mar 2, 2009                 Class adapted for POI 3.2 and for TaRGeT SPL.
 */
package com.motorola.btc.research.target.tc3output.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.UseCaseUtil;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.extensions.output.OutputDocumentExtension;
import com.motorola.btc.research.target.tcg.extractor.TextualStep;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;
import com.motorola.btc.research.target.tcg.propertiesreader.TestCaseProperties;

/**
 * This class contains the code for writing the test case excel file.
 * 
 * <pre>
 * CLASS:
 * The class receives a list of test cases and writes a excel file according to the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Builds and writes an Excel file from a list of test cases. 
 * 
 * COLABORATORS:
 * It depends on the POI external library.
 * 
 * USAGE:
 * ExcelFileFormatter efof = new ExcelFileFormatter(&lt;test_case_list&gt;);
 * efof.writeTestCaseDataInFile(&lt;file_object&gt;);
 * 
 */
public class ExcelFileFormatter extends OutputDocumentExtension {

	/** The introduction sheet */
	private HSSFSheet introSheet;

	/** The style of the hidden cells */
	private HSSFCellStyle hiddenCellStyle;


	/**
	 * The class constructor. It receives as input the list of test case that
	 * will be written in the excel file.
	 * 
	 * @param testCaseList
	 *            The list of test cases that will compose the test suite in the
	 *            excel file.
	 */
	public ExcelFileFormatter() {
	}

	/**
	 * This method build the intro sheet.
	 */
	private void mountIntroSheet() {
		//INSPECT Changed by jrm687

		//loading documents revision history
//		ProjectManagerExternalFacade projectFacade = ProjectManagerExternalFacade.getInstance();
//		Collection<PhoneDocument> documents = projectFacade.getCurrentProject().getPhoneDocuments();

//		for (PhoneDocument phoneDocument : documents) {
//		this.revisionHistory.addAll(phoneDocument.getRevisionHistory());
//		}

		//setting columns width
		this.introSheet.setColumnWidth(1, 4000);
		this.introSheet.setColumnWidth(2, 4000);
		this.introSheet.setColumnWidth(3, 6000);
		this.introSheet.setColumnWidth(4, 6000);

		//creating "Test Suite automatically generated by"
		HSSFRow row = this.introSheet.createRow(4);
		HSSFCellStyle style1 = this.workBook.createCellStyle();
		HSSFFont font1 = this.workBook.createFont();
		font1.setFontHeightInPoints((short) 14);
		font1.setFontName("Arial");
		font1.setItalic(true);
		style1.setFont(font1);

		HSSFCell cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue(new HSSFRichTextString("Test Suite automatically generated by"));

		//creating "TaRGeT"
		HSSFCellStyle style2 = this.workBook.createCellStyle();
		HSSFFont font2 = this.workBook.createFont();
		font2.setFontHeightInPoints((short) 72);
		font2.setFontName("Arial");
		style2.setFont(font2);

		row = this.introSheet.createRow(6);
		cell = row.createCell(3);
		cell.setCellStyle(style2);
		cell.setCellValue(new HSSFRichTextString("TaRGeT"));

//		//creating template information

//		//document control number
//		row = this.introSheet.createRow(9);
//		cell = row.createCell((short) 1);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, true, true, false));
//		cell.setCellValue("Document Control Number:");
//		cell = row.createCell((short) 2);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, true, false, false));
//		cell = row.createCell((short) 3);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, true, false, false));
//		cell = row.createCell((short) 4);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, true, false, true));
//		//merging cells
//		Region r = new Region(9, (short)1, 9, (short)4);
//		this.introSheet.addMergedRegion(r);

//		//document version
//		row = this.introSheet.createRow(10);
//		cell = row.createCell((short) 1);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, false, true, false));
//		cell.setCellValue("Version:");
//		cell = row.createCell((short) 4);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(false, false, false, true));
//		//merging cells
//		r = new Region(10, (short)1, 10, (short)4);
//		this.introSheet.addMergedRegion(r);

//		//document date
//		row = this.introSheet.createRow(11);
//		cell = row.createCell((short) 1);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(true, false, true, false));
//		cell.setCellValue("Date:");
//		cell = row.createCell((short) 2);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(true, false, false, false));
//		cell = row.createCell((short) 3);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(true, false, false, false));
//		cell = row.createCell((short) 4);
//		cell.setCellStyle(this.createTemplateInfoCellStyle(true, false, false, true));
//		//merging cells
//		r = new Region(11, (short)1, 11, (short)4);
//		this.introSheet.addMergedRegion(r);

//		//creating revision history table header
//		row = this.introSheet.createRow(14);
//		cell = row.createCell((short) 1);
//		cell.setCellStyle(this.headerCellStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue("Revision #");


//		cell = row.createCell((short) 2);
//		cell.setCellStyle(this.headerCellStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue("Date");

//		cell = row.createCell((short) 3);
//		cell.setCellStyle(this.headerCellStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue("Author");

//		cell = row.createCell((short) 4);
//		cell.setCellStyle(this.headerCellStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue("Description");

//		//adding document revisions
//		int initialRevisionRow = 15;
//		for (Revision revision : this.revisionHistory) {
//		row = this.introSheet.createRow(initialRevisionRow);
//		cell = row.createCell((short) 1);
//		cell.setCellStyle(this.textAreaStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue(revision.getRevisionNumber());

//		cell = row.createCell((short) 2);
//		cell.setCellStyle(this.textAreaStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue(revision.getRevisionDate());

//		cell = row.createCell((short) 3);
//		cell.setCellStyle(this.textAreaStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue(revision.getAuthor());

//		cell = row.createCell((short) 4);
//		cell.setCellStyle(this.textAreaStyle);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellValue(revision.getDescription());

//		initialRevisionRow++;
//		}
	}

	/**
	 * Creates a cell style and set the cell borders according to the given parameters.
	 * This method is used to create the template information area in the intro sheet.
	 * @param borderBottom if the cell style will have a bottom border
	 * @param borderTop if the cell style will have a top border
	 * @param borderLeft if the cell style will have a left border
	 * @param borderRight if the cell style will have a right border
	 * @return a cell style with the desired borders
	 */
	public HSSFCellStyle createTemplateInfoCellStyle(boolean borderBottom, boolean borderTop, 
			boolean borderLeft, boolean borderRight)
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();
		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		if(borderBottom){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		if(borderTop){
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if(borderLeft){
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
		if(borderRight){
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		return cellStyle;
	}

	private void initWorkbookAndStyles() 
	{
		this.workBook = new HSSFWorkbook();

		this.introSheet = this.workBook.createSheet("Test Suite Intro");
		this.tcSheet = this.workBook.createSheet("Test Suite");
		this.greyAreaStyle = this.getGreyAreaStyle();
		this.headerCellStyle = this.getHeaderCellStyle();
		this.textAreaStyle = this.getTextAreaStyle();
		this.hiddenCellStyle = this.getHiddenCellStyle();
	}

	/**
	 * Method that should be used to write the excel file.
	 * 
	 * @param file
	 *            The file where the excel information will be written.
	 * @throws IOException
	 *             Exception launched in case of something goes wrong during the
	 *             file writing
	 */
	public void writeTestCaseDataInFile(File file) throws IOException
	{
		this.initWorkbookAndStyles();

		this.createRTMRequirementsSheet();
		this.createRTMSystemTestSheet();
		this.createRTMUseCaseSheet();

		this.nextLine = 0;
		this.mountIntroSheet();
		this.mountHeader();

		int tcCount = TestCaseProperties.getInstance().getTestCaseInitialId();

		for (TextualTestCase testCase : this.testCases) 
		{
			this.mountTestCaseInfo(tcCount++, testCase);
			this.mountUseCaseReferences(testCase);
			this.mountTestCaseInitialFields(testCase);
			this.mountTestCaseNotes(testCase);
			this.mountTestCaseSteps(testCase);
			this.mountTestCaseFinalFields(testCase);
		}

		FileOutputStream fos = new FileOutputStream(file);
		this.workBook.write(fos);
		fos.close();
	}

	/**
	 * Constructs the HSSFCellStyle object of a header cell.
	 * 
	 * @return The style of a header area.
	 */
	private HSSFCellStyle getHeaderCellStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a grey cell.
	 * 
	 * @return The style of a grey area.
	 */
	private HSSFCellStyle getGreyAreaStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a text cell.
	 * 
	 * @return The style of a text area.
	 */
	private HSSFCellStyle getTextAreaStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}

	/**
	 * Constructs the HSSFCellStyle object of a hidden cell.
	 * 
	 * @return The style of a hidden area.
	 */
	private HSSFCellStyle getHiddenCellStyle() 
	{
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();

		HSSFFont font = this.workBook.createFont();
		font.setFontHeightInPoints((short) 2);
		font.setFontName("Arial");
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		
		return cellStyle;
	}

	/**
	 * This method mounts the test case info: case number, regression level,
	 * execution type, test case name, objective and expected results.
	 * 
	 * @param tcCount
	 *            The case number inside the generated test suite.
	 * @param testCase
	 *            The test case object where the information will be retrieved.
	 */
	protected void mountTestCaseInfo(int tcCount, TextualTestCase testCase) 
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);

		// Case number
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(testCase.getTcIdHeader() + tcCount + ""));
		cell.setCellStyle(this.textAreaStyle);

		// Reg Level
		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(testCase.getRegressionLevel()));
		cell.setCellStyle(this.textAreaStyle);

		// Exe Type
		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString(testCase.getExecutionType()));
		cell.setCellStyle(this.textAreaStyle);

		// Description/Name
		cell = row.createCell(3);
		String description = testCase.getDescription();
		cell.setCellValue(new HSSFRichTextString(description));
		cell.setCellStyle(this.textAreaStyle);

		// Procedure/Objective
		cell = row.createCell(4);
		String objective = testCase.getObjective();
		cell.setCellValue(new HSSFRichTextString(objective));
		cell.setCellStyle(this.textAreaStyle);

		// Expected Results
		cell = row.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
	}

	/**
	 * Adds a row with the use case references.
	 * 
	 * @param testCase
	 *            The test case from which the use case references will be
	 *            extracted.
	 */
	protected void mountUseCaseReferences(TextualTestCase testCase) 
	{
		HSSFRow row = this.tcSheet.createRow(this.nextLine++);
		this.mountGreyArea(row);

		// The label
		HSSFCell cell = row.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Use Cases: "));
		cell.setCellStyle(this.textAreaStyle);

		// The references
		cell = row.createCell(4);
		cell.setCellValue(new HSSFRichTextString(testCase.getUseCaseReferences()));
		cell.setCellStyle(this.textAreaStyle);

		// An empty cell
		cell = row.createCell(5);
		cell.setCellValue(new HSSFRichTextString(""));
		cell.setCellStyle(this.textAreaStyle);
	}

	/**
	 * Builds the beginning grey area of a test case row.
	 * 
	 * @param row
	 *            The row where the grey are will be built
	 */
	protected void mountGreyArea(HSSFRow row) 
	{

		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(this.greyAreaStyle);

		cell = row.createCell(1);
		cell.setCellStyle(this.greyAreaStyle);

		cell = row.createCell(2);
		cell.setCellStyle(this.greyAreaStyle);
	}

	/**
	 * Mounts the test case notes field.
	 * 
	 * @param testCase
	 *            The <code>testCase</code> that contains the notes.
	 */
	protected void mountTestCaseNotes(TextualTestCase testCase) 
	{
		HSSFRow notesArea = this.tcSheet.createRow(this.nextLine++);

		HSSFCell cell = null;

		this.mountGreyArea(notesArea);

		// Creating notes header
		cell = notesArea.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Notes:"));
		cell.setCellStyle(this.textAreaStyle);

		// String notes text
		String notes = testCase.getNote();
		cell = notesArea.createCell(4);
		cell.setCellValue(new HSSFRichTextString(notes));
		cell.setCellStyle(this.textAreaStyle);

		// Creating empty cells
		cell = notesArea.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
	}

	/**
	 * Mounts the test case steps fields.
	 * 
	 * @param testCase
	 *            The test case object that contains the step fields.
	 */
	protected void mountTestCaseSteps(TextualTestCase testCase) 
	{
		HSSFRow tcStepHeader = this.tcSheet.createRow(this.nextLine++);

		HSSFCell cell = null;

		this.mountGreyArea(tcStepHeader);

		cell = tcStepHeader.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Test Procedure (Step Number):"));
		cell.setCellStyle(this.textAreaStyle);

		cell = tcStepHeader.createCell(4);
		cell.setCellStyle(this.textAreaStyle);

		cell = tcStepHeader.createCell(5);
		cell.setCellStyle(this.textAreaStyle);

		int stepNumber = 1;
		for (TextualStep step : testCase.getSteps()) {
			HSSFRow row = this.tcSheet.createRow(this.nextLine++);
			this.mountGreyArea(row);

			// Step Number
			cell = row.createCell(3);
			cell.setCellValue(new HSSFRichTextString(stepNumber + ""));
			cell.setCellStyle(this.textAreaStyle);

			// Step
			cell = row.createCell(4);
			cell.setCellStyle(this.textAreaStyle);
			cell.setCellValue(new HSSFRichTextString(step.getAction()));

			// Expected Result
			cell = row.createCell(5);
			cell.setCellStyle(this.textAreaStyle);
			cell.setCellValue(new HSSFRichTextString(step.getResponse()));

			if (TestCaseProperties.getInstance().isPrintingHiddenTestCaseId()) 
			{
				// Hidden Id
				cell = row.createCell(6);
				cell.setCellStyle(this.hiddenCellStyle);
				cell.setCellValue(new HSSFRichTextString(step.getId().toString()));
			}

			stepNumber++;
		}
	}

	/**
	 * This method mounts the test case final conditions and cleanup
	 * information.
	 * 
	 * @param testCase
	 *            The test case that contains the final conditions and cleanup
	 *            information
	 */
	protected void mountTestCaseFinalFields(TextualTestCase testCase) 
	{
		// Creating the final condition and cleanup rows
		HSSFRow finalCondRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow cleanupRow = this.tcSheet.createRow(this.nextLine++);

		HSSFCell cell = null;

		this.mountGreyArea(finalCondRow);
		this.mountGreyArea(cleanupRow);

		// Setting the header
		cell = finalCondRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Final Conditions:"));
		cell.setCellStyle(this.textAreaStyle);
		cell = cleanupRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Cleanup:"));
		cell.setCellStyle(this.textAreaStyle);

		// preparing final conditions text.
		cell = finalCondRow.createCell(4);
		cell.setCellValue(new HSSFRichTextString(testCase.getFinalConditions()));
		cell.setCellStyle(this.textAreaStyle);

		// Creating an empty cell for cleanup field
		cell = cleanupRow.createCell(4);
		cell.setCellStyle(this.textAreaStyle);
		cell.setCellValue(new HSSFRichTextString(testCase.getCleanup()));

		// Creating empty cells
		cell = finalCondRow.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
		cell = cleanupRow.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
	}

	/**
	 * This method mounts the test case initial conditions, setup information
	 * and related requirements.
	 * 
	 * @param testCase
	 *            The test case that contains the initial conditions, setup
	 *            information and related requirements.
	 */
	protected void mountTestCaseInitialFields(TextualTestCase testCase) 
	{
		// Creating initial condition and setup rows
		HSSFRow requirementsRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow setupRow = this.tcSheet.createRow(this.nextLine++);
		HSSFRow iniCondRow = this.tcSheet.createRow(this.nextLine++);

		HSSFCell cell = null;

		this.mountGreyArea(requirementsRow);
		this.mountGreyArea(setupRow);
		this.mountGreyArea(iniCondRow);

		// Preparing the header
		cell = requirementsRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Requirements:"));
		cell.setCellStyle(this.textAreaStyle);
		cell = setupRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Setup:"));
		cell.setCellStyle(this.textAreaStyle);
		cell = iniCondRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Initial Conditions:"));
		cell.setCellStyle(this.textAreaStyle);

		// Preparing Requirements Information
		cell = requirementsRow.createCell(4);
		cell.setCellValue(new HSSFRichTextString(testCase.getRequirements()));
		cell.setCellStyle(this.textAreaStyle);

		// Preparing setup information
		cell = setupRow.createCell(4);
		cell.setCellStyle(this.textAreaStyle);
		cell.setCellValue(new HSSFRichTextString(testCase.getSetups()));

		// Preparing initial conditions text.
		cell = iniCondRow.createCell(4);
		cell.setCellValue(new HSSFRichTextString(testCase.getInitialConditions()));
		cell.setCellStyle(this.textAreaStyle);

		// Creating empty cells
		cell = requirementsRow.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
		cell = setupRow.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
		cell = iniCondRow.createCell(5);
		cell.setCellStyle(this.textAreaStyle);
	}

	/**
	 * This method mounts the test suite header.
	 */
	protected void mountHeader() 
	{

		HSSFRow row = this.tcSheet.createRow(0);
		row.setHeight((short) 700);

		this.tcSheet.setColumnWidth(0, 1600);
		this.tcSheet.setColumnWidth(1, 1800);
		this.tcSheet.setColumnWidth(2, 1800);
		this.tcSheet.setColumnWidth(3, 6000);
		this.tcSheet.setColumnWidth(4, 6000);
		this.tcSheet.setColumnWidth(5, 6000);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("Case"));
		cell.setCellStyle(this.headerCellStyle);

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString("Reg. Level"));
		cell.setCellStyle(this.headerCellStyle);

		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString("Exe. Type"));
		cell.setCellStyle(this.headerCellStyle);

		cell = row.createCell(3);
		cell.setCellValue(new HSSFRichTextString("Case Description"));
		cell.setCellStyle(this.headerCellStyle);

		cell = row.createCell(4);
		cell.setCellValue(new HSSFRichTextString("Procedure"));
		cell.setCellStyle(this.headerCellStyle);

		cell = row.createCell(5);
		cell.setCellValue(new HSSFRichTextString("Expected Results"));
		cell.setCellStyle(this.headerCellStyle);

		this.nextLine = 1;
	}

	/**
	 * Creates a traceability matrix that links requirements to use cases.
	 */
	private void createRTMRequirementsSheet() 
	{
		HashMap<String, List<String>> columns = new HashMap<String, List<String>>();
		
		for (String requirement : ProjectManagerExternalFacade.getInstance()
				.getAllReferencedRequirementsOrdered()) 
		{
			columns.put(requirement, this
					.getUseCasesFromRequirement(requirement));
		}
		
		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - Requirement",
				"Requirements Traceability Matrix - Requirements",
				"Requirement", "Use Case", columns, "REQ", "UC", this.workBook);
	}

	/**
	 * Creates a traceability matrix that links requirements to system test
	 * cases.
	 */
	private void createRTMSystemTestSheet() 
	{
		HashMap<String, List<String>> columns = new HashMap<String, List<String>>();
		
		for (String requirement : ProjectManagerExternalFacade.getInstance()
				.getAllReferencedRequirementsOrdered()) {
			columns.put(requirement, this
					.getTestCasesFromRequirement(requirement));
		}
		
		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - System Test",
				"Requirements Traceability Matrix - System Test",
				"Requirement", "System Test Case", columns, "REQ", "TES",
				this.workBook);
	}

	/**
	 * Creates a traceability matrix that links use cases to the system test
	 * cases.
	 */
	private void createRTMUseCaseSheet() 
	{
		List<String> tmpUseCases = this.getOrderedUseCases();
		HashMap<String, List<String>> columns = new HashMap<String, List<String>>();
		
		for (String usecaseId : tmpUseCases) 
		{
			columns.put(usecaseId, this.getTestCaseReferences(usecaseId));
		}

		TraceabilityMatrixGenerator.getInstance().createMatrix(
				"RTM - Use Case",
				"Requirements Traceability Matrix - Use Case", "Use Case",
				"System Test Case", columns, "UC", "TES", this.workBook);
	}

	/**
	 * Gets a list of use cases from the specified <code>requirement</code>.
	 * 
	 * @param requirement
	 *            The requirement whose use cases will be returned.
	 * @return A list of use cases.
	 */
	protected List<String> getUseCasesFromRequirement(String requirement) 
	{
		List<String> result = new ArrayList<String>();

		for (UseCase usecase : ProjectManagerExternalFacade.getInstance()
				.getUseCasesByRequirement(requirement)) 
		{
			result.add(usecase.getId());
		}

		String[] orderedUseCases = result.toArray(new String[] {});
		Arrays.sort(orderedUseCases);

		return new ArrayList<String>(Arrays.asList(orderedUseCases));
	}

	/**
	 * Returns a list of test case ids related to the given
	 * <code>requirement</code>.
	 * 
	 * @param requirement
	 *            The requirement whose test cases will be returned.
	 * @return A list of test cases.
	 */
	protected List<String> getTestCasesFromRequirement(String requirement) 
	{
		List<String> result = new ArrayList<String>();

		for (TextualTestCase testcase : this.testCases) 
		{
			if (testcase.coversRequirement(requirement)) 
			{
				result.add(testcase.getTcIdHeader());
			}
		}

		return result;
	}

	/**
	 * Returns a list of test case ids related to the given
	 * <code>usecaseId</code>.
	 * 
	 * @param usecaseId
	 *            The id of the use case whose related test cases will be
	 *            returned.
	 * @return A list of test cases.
	 */
	protected List<String> getTestCaseReferences(String usecaseId) 
	{
		List<String> result = new ArrayList<String>();

		for (TextualTestCase testcase : this.testCases) 
		{
			if (testcase.coversUseCase(usecaseId))
			{
				result.add(testcase.getTcIdHeader());
			}
		}

		return result;
	}

	/**
	 * Returns a list of lexicographically ordered strings of use cases.
	 * 
	 * @return A list with the ordered use cases as strings.
	 */
	protected List<String> getOrderedUseCases() 
	{
		Set<String> set = new HashSet<String>();

		for (Feature feature : ProjectManagerExternalFacade.getInstance()
				.getCurrentProject().getFeatures()) 
		{
			for (UseCase usecase : feature.getUseCases()) 
			{
				set.add(UseCaseUtil.getUseCaseReference(feature, usecase));
			}
		}

		String[] sorted = set.toArray(new String[] {});
		Arrays.sort(sorted);

		return new ArrayList<String>(Arrays.asList(sorted));
	}
}
