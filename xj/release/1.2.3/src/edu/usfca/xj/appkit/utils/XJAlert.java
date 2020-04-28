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
import edu.usfca.xj.foundation.XJSystem;

import javax.swing.*;
import java.awt.*;

public class XJAlert {

    public static final int YES = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CANCEL = JOptionPane.CANCEL_OPTION;

    public static void display(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent==null?XJApplication.getActiveContainer():parent,
                message, title, JOptionPane.INFORMATION_MESSAGE, null);
    }

    public static int displayAlertYESNO(Component parent, String title, String message) {
        return JOptionPane.showConfirmDialog(parent==null?XJApplication.getActiveContainer():parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public static int displayAlertYESNOCANCEL(Component parent, String title, String message) {
        return JOptionPane.showConfirmDialog(parent==null?XJApplication.getActiveContainer():parent,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    public static int displayAlert(Component parent, String title, String message, String b1, String b2, int def) {
        return displayCustomAlert(parent, title, message, new String[] { b1, b2}, def);
    }

    public static int displayAlert(Component parent, String title, String message, String b1, String b2, String b3, int def) {
        return displayCustomAlert(parent, title, message, new String[] { b1, b2, b3 }, def);
    }

    public static int displayCustomAlert(Component parent, String title, String message, String[] buttons, int def) {
        if(XJSystem.isMacOS()) {
            String [] reverse = new String[buttons.length];
            for(int i=0; i<buttons.length; i++) {
                reverse[i] = buttons[buttons.length-i-1];
            }
            buttons = reverse;
            def = buttons.length-def-1;
        }
        int result = JOptionPane.showOptionDialog(parent==null?XJApplication.getActiveContainer():parent,
                message, title, 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[def]);
        if(XJSystem.isMacOS()) {
            return buttons.length-result-1;
        } else
            return result;
    }
}
