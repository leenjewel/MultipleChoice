/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.view.iview;

import org.leenjewel.multiplechoice.model.imodel.IOption;

/**
 *
 * @author leenjewel
 */
public interface IOptionView extends IView {

    public IOption getOptionModel();

    public void unSelected();

    public void Selected();
}
