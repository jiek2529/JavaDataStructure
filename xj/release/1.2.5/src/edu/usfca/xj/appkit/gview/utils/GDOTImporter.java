package edu.usfca.xj.appkit.gview.utils;

import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementCircle;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*

[The "BSD licence"]
Copyright (c) 2005-2006 Jean Bovet
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

public abstract class GDOTImporter {

    protected GElement graph;
    protected float height = 0;

    public GElement generateGraph(String dotFile) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(dotFile));
        graph = null;

        /** The problem here is that DOT sometime inserts a '\' at the end of a long
         * line so we have to skip it and continue to parse until a "real" EOL is reached.
         * Example:
         * 	statement -> compoundStatement [pos="e,3264,507 3271,2417 3293,2392 ... 3237,565 3234,560 32\
            39,545 3243,534 3249,523 3257,514"];
         */

        StringBuffer line = new StringBuffer();
        int c;  // current character
        int pc = -1;    // previous character
        while((c = br.read()) != -1) {
            if(c == '\n' && pc != '\\') {
                GElement element = parseLine(line.toString());
                if(element != null) {
                    if(graph == null)
                        graph = element;
                    else
                        graph.addElement(element);
                }
                line.delete(0, line.length());
            } else if(c != '\n' && c != '\r' && c != '\\') {
                line.append((char)c);
            }
            pc = c;
        }
        return graph;
    }

    public String[] parseTokens(String line) throws IOException {
        List tokens = new ArrayList();

        StreamTokenizer st = new StreamTokenizer(new StringReader(line));
        st.parseNumbers();
        st.wordChars('_', '_'); // A word can be THIS_IS_A_WORD

        int token = st.nextToken();
        while(token != StreamTokenizer.TT_EOF) {
            String element = null;
            switch(token) {
                case StreamTokenizer.TT_NUMBER:
                    element = String.valueOf(st.nval);
                    break;
                case StreamTokenizer.TT_WORD:
                    element = st.sval;
                    break;
                case '"':
                case '\'':
                    element = st.sval;
                    break;
                case StreamTokenizer.TT_EOL:
                    break;
                case StreamTokenizer.TT_EOF:
                    break;
                default:
                    element = String.valueOf((char)st.ttype);
                    break;
            }
            if(element != null)
                tokens.add(element);
            token = st.nextToken();
        }

        String[] result = new String[tokens.size()];
        for(int index=0; index<tokens.size(); index++)
            result[index] = (String)tokens.get(index);
        return result;
    }

    public abstract GElement parseLine(String line) throws IOException;

    public abstract GElement createGraphNode(String[] tokens) throws IOException;
    public abstract GElement createGraphEdge(String[] tokens) throws IOException;

    public boolean isFloatString(String s) {
        try {
            Float.parseFloat(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public class Node extends GElementCircle {

        public boolean doublecircle;
        public float width, height;

        public void setDouble(boolean flag) {
            doublecircle = flag;
        }

        public void setSize(float width, float height) {
            this.width = width;
            this.height = height;
        }

        public void drawShape(Graphics2D g) {
            //super.drawShape(g);

            {
                int x = (int)(getPositionX()-width/2);
                int y = (int)(getPositionY()-height/2);

                g.drawOval(x, y, (int)width, (int)height);
            }

            if(doublecircle) {
                int x = (int)(getPositionX()-width/2);
                int y = (int)(getPositionY()-height/2);

                g.drawOval(x+3, y+3, (int)(width-6), (int)(height-6));
            }
        }

    }

}
