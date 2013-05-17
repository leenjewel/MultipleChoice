/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.leenjewel.multiplechoice.extensions.IMultipleChoiceExtensions;
import org.leenjewel.multiplechoice.extensions.MCExtManager;
import org.leenjewel.multiplechoice.lib.XMLFile;
import org.leenjewel.multiplechoice.model.Question;
import org.leenjewel.multiplechoice.model.imodel.IQuestion;
import org.leenjewel.multiplechoice.model.imodel.ITopic;
import org.leenjewel.multiplechoice.view.iview.IQuestionView;
import org.leenjewel.multiplechoice.view.iview.ITopicView;
import org.xml.sax.SAXException;

/**
 *
 * @author leenjewel
 */
public class QuestionView extends javax.swing.JPanel implements IQuestionView{

    private IQuestion questionModel = null;

    private ArrayList<ITopicView> topicViews = null;

    private Frame parentFrame = null;

    private long startTime = -1;

    private long doTime = -1;

    public QuestionView(IQuestion question, Frame frame) {
        super();
        questionModel = question;
        parentFrame = frame;
        initComponents();
    }

    public QuestionView(File zip, Frame frame) {
        parentFrame = frame;
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
                    MCExtManager.getInstance().loadExtClass(questionModel.getId(), zip);
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
            BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(layout);

            for (ITopic topic : questionModel.getTopics()) {
                TopicView topicView = new TopicView(this, topic);
                this.add(topicView);
            }

            javax.swing.JButton submitBtn = new javax.swing.JButton("提交问卷");
            submitBtn.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (isAllDone()) {
                        endDoQuestion();
                        onSubmit(ae);
                    } else {
                        JOptionPane.showMessageDialog(getAppFrame(), "你的问卷还未答完，不能交卷，请仔细作答！", "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            this.add(submitBtn);
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

    private void onSubmit(ActionEvent ae) {
        if (questionModel != null) {
            try {
                IMultipleChoiceExtensions ext = MCExtManager.getInstance().getExtension(questionModel.getId());
                ext.runExtensions(this);
            } catch (InstantiationException ex) {
                Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<ITopicView> getTopicViews() {
        if (topicViews == null) {
            topicViews = new ArrayList<ITopicView>();
        }
        return topicViews;
    }

    @Override
    public Frame getAppFrame() {
        return parentFrame;
    }

    @Override
    public void startDoQuestion() {
        if (startTime < 0) {
            startTime = (new Date()).getTime();
        }
    }

    @Override
    public void endDoQuestion() {
        if (doTime < 0) {
            doTime = (new Date()).getTime() - startTime;
        }
    }

    @Override
    public long getDoTime() {
        return doTime;
    }

    @Override
    public boolean isAllDone() {
        for (ITopicView topicView : getTopicViews()) {
            if (topicView.hasDone() == false) { return false; }
        }
        return true;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }
}