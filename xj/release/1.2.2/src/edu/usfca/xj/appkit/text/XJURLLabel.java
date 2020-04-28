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

package edu.usfca.xj.appkit.text;

import edu.usfca.xj.appkit.utils.BrowserLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

// This code has been inspired from:
// http://www.rgagnon.com/javadetails/java-0273.html
// Written and compiled by Real Gagnon (c)1998-2000

public class XJURLLabel extends JLabel  {

    protected String url;
    protected Color unvisitedURL = Color.blue;
    protected Color visitedURL = Color.blue;     // currently not used

    public XJURLLabel(String url) {
        setForeground(unvisitedURL);
        try {
            this.url = url;
            addMouseListener( new Clicked() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnvisitedURLColor(Color c) {
        unvisitedURL = c;
    }

    public void setVisitedURLColor(Color c) {
        visitedURL = c;
    }

    class Clicked extends MouseAdapter{
        public void mouseClicked(MouseEvent me){
            setForeground(visitedURL);
            try {
                BrowserLauncher.openURL(url);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}