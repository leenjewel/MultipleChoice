/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.model;

import java.util.ArrayList;
import org.leenjewel.multiplechoice.lib.XMLFile;
import org.leenjewel.multiplechoice.model.imodel.IOption;
import org.leenjewel.multiplechoice.model.imodel.IQuestion;
import org.leenjewel.multiplechoice.model.imodel.ITopic;

/**
 *
 * @author leenjewel
 */
public class Question implements IQuestion{

    private XMLFile head = null;

    private String title = null;

    private ArrayList<ITopic> topics = null;

    private void buildFromXML(XMLFile source) {
        head = source.get("head");
        title = head.get("title").getText();
        XMLFile[] xtopics = source.gets("topic");
        for (int index = 0; index < xtopics.length; index++) {
            XMLFile topic = xtopics[index];
            String id = topic.get("id").getText();
            String content = topic.get("content").getText();
            Boolean multiple = topic.get("multiple").getBoolean();
            ITopic t = new Topic();
            t.setId(id);
            t.setContent(content);
            if (multiple == true) {
                t.setMultipleTopic(true);
            }

            XMLFile[] options = topic.gets("option");
            for (int tindex = 0; tindex < options.length; tindex++) {
                XMLFile option = options[tindex];
                IOption o = new Option();
                o.setContent(option.get("content").getText());
                o.setOption(option.get("id").getText());
                t.addOption(o);
            }
            addTopic(t);

        }
    }

    public Question(XMLFile source) {
        buildFromXML(source);
    }

    public ArrayList<ITopic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<ITopic>();
        }
        return topics;
    }

    public void addTopic(ITopic topic) {
        getTopics().add(topic);
    }

    public String getTitle() {
        return title;
    }

}
