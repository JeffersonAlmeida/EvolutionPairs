/*
 * @(#)ExceFileFilter.java
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
 * dhq348   Jan 26, 2007    LIBll12753   Initial creation.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 */
package br.ufpe.cin.target.common.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Implements a FileFilter to Microsoft Excel files.
 * 
 * <pre>
 * CLASS:
 * Implements a <code>FileFilter</code> to Microsoft Excel files. This filter accepts only files that end with '.xls'.
 */
public class ExcelFileFilter implements FileFilter
{

    /**
     * This method accepts file names that ends with '.xls'.
     * 
     * @param file The file which extension will be checked.
     * @return <i>True</i> if the file ends with '.xls' or <i>false</i> otherwise.
     */
    public boolean accept(File file)
    {
        return !file.isDirectory() && file.getAbsolutePath().endsWith(".xls");
    }
}
