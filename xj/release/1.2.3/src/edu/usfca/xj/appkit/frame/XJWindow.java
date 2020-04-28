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

package edu.usfca.xj.appkit.frame;

import edu.usfca.xj.appkit.app.XJApplication;
import edu.usfca.xj.appkit.document.XJData;
import edu.usfca.xj.appkit.document.XJDocument;
import edu.usfca.xj.appkit.menu.XJMainMenuBar;
import edu.usfca.xj.appkit.menu.XJMenu;
import edu.usfca.xj.appkit.menu.XJMenuItem;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class XJWindow extends XJFrame {

    protected XJDocument document = null;

    public XJWindow() {
        XJApplication.shared().addWindow(this);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowEventListener());
    }

    public boolean isAuxiliaryWindow() {
        return false;
    }

    public boolean shouldAppearsInWindowMenu() {
        return true;
    }

    public void setDocument(XJDocument document) {
        this.document = document;
    }

    public XJDocument getDocument() {
        return document;
    }

    public XJData getDocumentData() {
        if(document != null)
            return document.getDocumentData();
        else
            return null;
    }

    public void close() {
        XJApplication.shared().removeWindow(this);
        super.close();
    }

    public void performClose() {
        if(document == null)
            close();
        else
            document.performClose();
    }

    public void menuItemState(XJMenuItem item) {
        if(document == null) {
            if(item.getTag() == XJMainMenuBar.MI_CLOSE)
                item.setEnabled(true);
            else
                item.setEnabled(false);
        }
    }

    public void handleMenuEvent(XJMenu menu, XJMenuItem item) {
        if(item.getTag() == XJMainMenuBar.MI_CLOSE) {
            performClose();
        }
    }

    protected long lastModifiedOnDisk = 0;

    public long getLastModifiedOnDisk() {
        if(getDocument() == null)
            return 0;
        if(getDocument().getDocumentPath() == null)
            return 0;

        File f = null;
        try {
            f = new File(getDocument().getDocumentPath());
        } catch(Exception e) {
            // ignore excepton
        }

        if(f == null)
            return 0;
        else
            return f.lastModified();
    }

    public void synchronizeLastModifiedDate() {
        lastModifiedOnDisk = getLastModifiedOnDisk();
    }

    public void windowActivated() {
        if(lastModifiedOnDisk != 0 && lastModifiedOnDisk != getLastModifiedOnDisk()) {
            windowDocumentPathDidChange();
        }
        synchronizeLastModifiedDate();
    }

    public void windowDocumentPathDidChange() {
        // can be used by subclasses to perform something when the document
        // associated file has changed (based on the file modification date)
    }

    // *** Private methods

    private class WindowEventListener extends WindowAdapter {

        public void windowActivated(WindowEvent e) {
            if(mainMenuBar != null)
                mainMenuBar.refresh();
        }

        public void windowClosing(WindowEvent e) {
            performClose();
        }
    }
}
