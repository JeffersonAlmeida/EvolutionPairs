/*
 * @(#)TargetApplication.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348         -         LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * dwvm83	 Nov 04, 2008				 The interface IPlatformRunnable has been deprecated and the IApplication interface is used instead.
 * wmr068    Aug 07, 2008   LIBqq64190   Methods checkDatFile and checkDotNet transfered to the MS Word plug-in.
 */
package br.ufpe.cin.target.core;

import java.util.Collection;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.core.extensions.StartingOperationsExtension;
import br.ufpe.cin.target.core.extensions.StartingOperationsExtensionFactory;

/**
 * This class controls all aspects of the application's execution.
 * 
 * <pre>
 * CLASS:
 * This class controls all aspects of the application's execution. 
 * It is responsible for running all the application.
 */
public class TargetApplication implements IApplication
{
	/**
	 * Instantiates the SEPARATOR with the system SEPARATOR string and gets the system user
	 * directory in which the application has been launched from.
	 */
	public TargetApplication()
	{
	}

    /**
     * Starts this application with the given context and returns a result.  This 
	 * method must not exit until the application is finished and is ready to exit.
	 * The content of the context is unchecked and should conform to the expectations of
	 * the application being invoked.
     * 
	 * @param context the application context to pass to the application
	 * @return the return value of the application
	 * @exception Exception if there is a problem running this application.
	 */
	public Object start(org.eclipse.equinox.app.IApplicationContext context)  
	{
	    //INSPECT: Adding better tabs to TaRGeT.
	    PlatformUI.getPreferenceStore().setDefault(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
	    
	    Display display = PlatformUI.createDisplay();
		Object result = null;
		try
		{
			
			Collection<StartingOperationsExtension> startingOperationsExtensions = StartingOperationsExtensionFactory
			.startingOperationsExtensions();

			boolean continueStartingOperations = true;

			for (StartingOperationsExtension startingOperationsExtension : startingOperationsExtensions)
			{
				continueStartingOperations = startingOperationsExtension.performStartingOperations();

				if (!continueStartingOperations) {
					break;
				}
			}

			if (continueStartingOperations)
			{
				int returnCode = PlatformUI.createAndRunWorkbench(display,
						new ApplicationWorkbenchAdvisor());
				if (returnCode == PlatformUI.RETURN_RESTART)
				{
					result = IApplication.EXIT_RESTART;
				}
				else
				{
					result = IApplication.EXIT_OK;
				}
			}
		}
		finally
		{
			display.dispose();
		}
		return result;
	}

	
	public void stop() 
	{
		
	}
}
