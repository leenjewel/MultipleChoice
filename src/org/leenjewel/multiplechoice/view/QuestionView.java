/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.ParserConfigurationException;
import org.jdesktop.layout.GroupLayout;
import org.leenjewel.multiplechoice.lib.XMLFile;
import org.leenjewel.multiplechoice.model.Question;
import org.leenjewel.multiplechoice.model.imodel.IQuestion;
import org.leenjewel.multiplechoice.model.imodel.ITopic;
import org.leenjewel.multiplechoice.view.iview.IQuestionView;
import org.xml.sax.SAXException;

/**
 *
 * @author leenjewel
 */
public class QuestionView extends javax.swing.JPanel implements IQuestionView{

    private IQuestion questionModel = null;

    public QuestionView(IQuestion question) {
        super();
        questionModel = question;
        initComponents();
    }

    public QuestionView(File zip) {
        try {
            ZipFile zipFile = new ZipFile(zip);
            Enumeration emu = zipFile.entries();
            int i = 0;
            while(emu.hasMoreElements()){
                ZipEntry entry = ((ZipEntry)emu.nextElement());
                if (entry.isDirectory()){
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                if ("data.xml".equalsIgnoreCase(entry.getName())){
                    XMLFile questionXML = new XMLFile(bis);
                    questionModel = new Question(questionXML);
                } else if (entry.getName().endsWith(".class")) {
                    try {
                        CalculateClassLoader classLoader = new CalculateClassLoader(new URL[] {}, QuestionView.class.getClassLoader());
                        // load jar to classpath
                        classLoader.addURL(new File(zip.getName()).toURI().toURL());
                        // get class full name(with package)
                        String className = entry.getName().replace("/", ".");
                        className = className.substring(0, className.length() - 6);
                        // use reflect to get instance
                        //Class<?> mainClass = Class.forName(className, true, classLoader);
                        Class mainClass = classLoader.loadClass(className);
                        // find main method
                        Method mainMethod = mainClass.getMethod("runExtensions", new Class[] {String[].class });
                        // check is REAL main method
                        if ((mainMethod.getModifiers() & (Modifier.STATIC | Modifier.PUBLIC)) == (Modifier.STATIC | Modifier.PUBLIC)) {
                            // do something
                            System.out.println(zip.getName() + ":" + className);
                            // call main method
                            // mainMethod.invoke(null, new Object[] {new String[] {} });
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
                bis.close();
            }
            zipFile.close();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }

    private void initComponents() {
        if (questionModel != null) {
            org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
            this.setLayout(layout);

            GroupLayout.ParallelGroup pg = layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING);

            GroupLayout.SequentialGroup sg = layout.createSequentialGroup()
                .addContainerGap();

            for (ITopic topic : questionModel.getTopics()) {
                TopicView topicView = new TopicView(topic);
                pg = pg.add(topicView, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE);
                sg = sg.add(topicView)
                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED);
            }

            javax.swing.JButton submitBtn = new javax.swing.JButton("提交问卷");
            pg = pg.add(submitBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE);
            sg = sg.add(submitBtn)
                   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED);

            layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .addContainerGap()
                    .add(pg)
                    .addContainerGap())
            );

            layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(sg.addContainerGap(256, Short.MAX_VALUE))
            );
        }
    }

    @Override
    public IQuestion getQuestionModel() {
        return questionModel;
    }

    @Override
    public Component getComponent() {
        return this;
    }
}

class CalculateClassLoader extends URLClassLoader {

    public CalculateClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

}