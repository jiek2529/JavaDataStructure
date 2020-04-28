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

import edu.usfca.xj.appkit.XJControl;
import edu.usfca.xj.appkit.menu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class XJFrame extends XJControl implements XJMenuBarCustomizer, XJMenuBarDelegate {

    private static final String PROPERTY_WINDOW_MODIFIED = "windowModified";

    protected XJMainMenuBar mainMenuBar;
    protected JFrame jFrame;
    protected XJFrameDelegate delegate;
    protected boolean alreadyBecomeVisible = false;
    protected boolean dirty = false;

    public XJFrame() {
        jFrame = new JFrame();
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                XJFrame.this.windowActivated();
            }
        });
        setDefaultSize();
    }

    public void awake() {
        if(shouldDisplayMainMenuBar()) {
            mainMenuBar = XJMainMenuBar.createInstance();
            mainMenuBar.setCustomizer(this);
            mainMenuBar.setDelegate(this);
            mainMenuBar.createMenuBar();
            jFrame.setJMenuBar(mainMenuBar.getJMenuBar());
        }
    }

    public void setDelegate(XJFrameDelegate delegate) {
        this.delegate = delegate;
    }

    public XJFrameDelegate getDelegate() {
        return delegate;
    }

    public void setDefaultCloseOperation(int operation) {
        jFrame.setDefaultCloseOperation(operation);
    }

    public Container getContentPane() {
        return jFrame.getContentPane();
    }

    public JRootPane getRootPane() {
        return jFrame.getRootPane();
    }

    public JLayeredPane getLayeredPane() {
        return jFrame.getLayeredPane();
    }

    public Component getGlassPane() {
        return jFrame.getGlassPane();
    }

    public XJMainMenuBar getMainMenuBar() {
        return mainMenuBar;
    }

    public void setTitle(String title) {
        jFrame.setTitle(title);
    }

    public String getTitle() {
        return jFrame.getTitle();
    }

    public void setSize(int dx, int dy) {
        jFrame.setSize(dx, dy);
    }

    public void setDefaultSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize((int)(dim.width*0.5), (int)(dim.height*0.5));
    }

    public void setResizable(boolean flag) {
        jFrame.setResizable(flag);
    }

    public void pack() {
        jFrame.pack();
    }

    public void bringToFront() {
        jFrame.toFront();
    }

    public void setVisible(boolean flag) {
        if(flag && !alreadyBecomeVisible) {
            alreadyBecomeVisible = true;
            becomingVisibleForTheFirstTime();
        }
        jFrame.setVisible(flag);
    }

    public void becomingVisibleForTheFirstTime() {

    }

    public boolean isVisible() {
        return jFrame.isVisible();
    }

    public boolean isActive() {
        return jFrame.isActive();
    }

    public void show() {
        setVisible(true);
    }

    public void showModal() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public boolean isCompletelyOnScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return jFrame.getX()+jFrame.getWidth()<dim.getWidth() && jFrame.getY()+jFrame.getHeight()<dim.getHeight();    
    }

    public void center() {
        jFrame.setLocationRelativeTo(null);
    }

    public void setPosition(int x, int y) {
        jFrame.setLocation(x, y);
    }

    public void offsetPosition(int dx, int dy) {
        Point p = jFrame.getLocation();
        jFrame.setLocation(p.x+dx, p.y+dy);
    }

    public void close() {
        XJMainMenuBar.removeInstance(mainMenuBar);
        if(mainMenuBar != null) {
            mainMenuBar.setDelegate(null);
            mainMenuBar = null;
        }

        jFrame.dispose();
        jFrame = null;

        if(delegate != null)
            delegate.frameDidClose(this);
    }

    public void setDirty() {
        // Use dirty member to speed up
        if(!dirty) {
            jFrame.getRootPane().putClientProperty(PROPERTY_WINDOW_MODIFIED, Boolean.TRUE);
            dirty = true;
        }
    }

    public void resetDirty() {
        if(dirty) {
            dirty = false;
            jFrame.getRootPane().putClientProperty(PROPERTY_WINDOW_MODIFIED, Boolean.FALSE);
        }
    }

    public boolean dirty() {
        Boolean b = (Boolean) jFrame.getRootPane().getClientProperty(PROPERTY_WINDOW_MODIFIED);
        if(b == null)
            return false;
        else
            return b.booleanValue();
    }

    public boolean shouldDisplayMainMenuBar() {
        return true;
    }

    public boolean shouldAppearsInWindowMenu() {
        return false;
    }

    public void windowActivated() {

    }

    public void customizeFileMenu(XJMenu menu) {

    }

    public void customizeWindowMenu(XJMenu menu) {

    }

    public void customizeHelpMenu(XJMenu menu) {

    }

    public void customizeMenuBar(XJMainMenuBar menubar) {

    }

    public void menuItemState(XJMenuItem item) {
    }

    public void handleMenuEvent(XJMenu menu, XJMenuItem item) {
    }

    public Container getJavaContainer() {
        return jFrame;
    }

    public JFrame getJFrame() {
        return jFrame;
    }
}
