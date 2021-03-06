/*

[The "BSD licence"]
Copyright (c) 2005 Jean Bovet
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package edu.usfca.xj.appkit.utils;

import edu.usfca.xj.appkit.app.XJApplication;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XJFileChooser {

    private static XJFileChooser shared = null;

    private List selectedFilePaths = null;
    private String selectedFilePath = null;

    public static XJFileChooser shared() {
        if(shared == null)
            shared = new XJFileChooser();
        return shared;
    }

    public boolean displayOpenDialog(Component parent, String extension, String description, boolean multiple) {
        List extensions = new ArrayList();
        extensions.add(extension);
        List descriptions = new ArrayList();
        descriptions.add(description);
        return displayOpenDialog(parent, extensions, descriptions, multiple);
    }

    public boolean displayOpenDialog(Component parent, List extensions, List descriptions, boolean multiple) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(multiple);
        if(extensions == null || extensions.size() == 0)
            chooser.setAcceptAllFileFilterUsed(false);
        else {
            for(int i=0; i<extensions.size(); i++) {
                XJFileFilter ff = XJFileFilter.createFileFilter((String)extensions.get(i),
                                                (String)descriptions.get(i));
                chooser.addChoosableFileFilter(ff);
                if(extensions.size() == 1 && i == 0)
                    chooser.setFileFilter(ff);
            }
            if(extensions.size() > 1)
                chooser.setFileFilter(chooser.getAcceptAllFileFilter());
        }

        if(selectedFilePath != null)
            chooser.setCurrentDirectory(new File(selectedFilePath));

        if(chooser.showOpenDialog(parent==null?null:parent) == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = chooser.getSelectedFile().getAbsolutePath();
            selectedFilePaths = filesToList(chooser.getSelectedFiles());
            if(extensions != null && extensions.size() >= 0) {
                FileFilter ff = chooser.getFileFilter();
                if(ff instanceof XJFileFilter) {
                    XJFileFilter filter = (XJFileFilter)ff;
                    if(!selectedFilePath.endsWith("."+filter.getExtension()))
                        selectedFilePath += "."+filter.getExtension();
                }
            }

            return XJApplication.YES;
        } else
            return XJApplication.NO;
    }

    private List filesToList(File[] files) {
        List array = new ArrayList();
        for(int i=0; i<files.length; i++)
            array.add(files[i].getAbsolutePath());
        return array;
    }

    public boolean displaySaveDialog(Component parent, String extension, String extensionDescription, boolean acceptAll) {
        return displaySaveDialog(parent, Collections.singletonList(extension),
                Collections.singletonList(extensionDescription), acceptAll);
    }

    public boolean displaySaveDialog(Component parent, List extensions, List descriptions, boolean acceptAll) {
        JFileChooser chooser = new JFileChooser();
        if(extensions == null || extensions.size() == 0)
            chooser.setAcceptAllFileFilterUsed(false);
        else {
            chooser.setAcceptAllFileFilterUsed(acceptAll);
            XJFileFilter firstFF = null;
            for(int i=0; i<extensions.size(); i++) {
                XJFileFilter ff = XJFileFilter.createFileFilter((String)extensions.get(i),
                                                (String)descriptions.get(i));
                chooser.addChoosableFileFilter(ff);
                if((extensions.size() == 1 || !acceptAll) && i == 0)
                    firstFF = ff;
            }
            if(extensions.size() > 1 && acceptAll)
                chooser.setFileFilter(chooser.getAcceptAllFileFilter());
            else
                chooser.setFileFilter(firstFF);
        }

        if(selectedFilePath != null)
            chooser.setCurrentDirectory(new File(selectedFilePath));

        if(chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = chooser.getSelectedFile().getAbsolutePath();
            selectedFilePaths = filesToList(chooser.getSelectedFiles());
            if(extensions != null && extensions.size() >= 0) {
                FileFilter ff = chooser.getFileFilter();
                if(ff instanceof XJFileFilter) {
                    XJFileFilter filter = (XJFileFilter)ff;
                    if(!selectedFilePath.endsWith("."+filter.getExtension()))
                        selectedFilePath += "."+filter.getExtension();
                }
            }
            return XJApplication.YES;
        } else
            return XJApplication.NO;
    }
        
    public boolean displayChooseDirectory(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = chooser.getSelectedFile().getAbsolutePath();
            return XJApplication.YES;
        } else
            return XJApplication.NO;
    }

    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    public List getSelectedFilePaths() {
        return selectedFilePaths;
    }
}
