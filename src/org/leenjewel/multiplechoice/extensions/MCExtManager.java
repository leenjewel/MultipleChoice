/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.extensions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.leenjewel.multiplechoice.model.imodel.IQuestion;
import org.leenjewel.multiplechoice.view.iview.IQuestionView;

/**
 *
 * @author leenjewel
 */
public class MCExtManager {

    static private MCExtManager instance = null;

    private HashMap<String, Class> extClasses = null;

    private MCExtManager(){
        extClasses = new HashMap<String, Class>();
    };

    static public MCExtManager getInstance() {
        if (instance == null) {
            instance = new MCExtManager();
        }
        return instance;
    }

    public String getExtClassName(String questionId) {
        return "org.leenjewel.multiplechoice.extensions.MCExt" + questionId;
    }

    public void loadExtClass(String questionId, File extFile) {
        String extClassName = getExtClassName(questionId);
        Class extClass = extClasses.get(extClassName);
        if (extClass == null) {
            try {
                URL[] us = {(extFile.toURI().toURL())};
                URLClassLoader classloader = new URLClassLoader(us, this.getClass().getClassLoader());
                extClass = classloader.loadClass(extClassName);
                extClasses.put(extClassName, extClass);
            } catch (MalformedURLException ex) {
                Logger.getLogger(MCExtManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MCExtManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public IMultipleChoiceExtensions getExtension(String questionId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String extClassName = getExtClassName(questionId);
        Class extClass = extClasses.get(extClassName);
        if (extClass != null) {
            return ((IMultipleChoiceExtensions)extClass.newInstance());
        }
        throw new ClassNotFoundException(extClassName + "not found.");
    }

}
