/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.multiplechoice.lib;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author leenjewel
 */
public class XMLFile {
    private Element xml_root;

    public Boolean save(String xml_path, String encode){
        try{
            File xml_fp = new File(xml_path);
            if (!xml_fp.exists()) xml_fp.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(xml_fp));
            output.write(new String(this.toCode().getBytes(),encode));
            output.close();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean save(String xml_path) {
        return save(xml_path, "UTF-8");
    }

    private String[][] dumpValues(String values){
        String[][] result = null;
        try{
            String[] attrs = values.split(",");
            if (attrs!=null && attrs.length>0){
                result = new String[attrs.length][2];
                for (int i=0;i<attrs.length;i++){
                    String[] attr = attrs[i].split("=");
                    if (attr.length==2){
                        result[i] = attr;
                    }
                    else{
                        result[i][0] = attr[0];
                        result[i][1] = null;
                    }
                }
            }
            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    public XMLFile(File xmlFile) throws ParserConfigurationException, SAXException, IOException{
    	DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
    	DocumentBuilder db=dbf.newDocumentBuilder();
    	Document doc = db.parse(xmlFile);
    	this.xml_root = doc.getDocumentElement();
    }

    public XMLFile(InputStream in) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
    	DocumentBuilder db=dbf.newDocumentBuilder();
    	Document doc = db.parse(in);
        this.xml_root = doc.getDocumentElement();
    }

    public XMLFile(Element xml_root){
        this.xml_root = xml_root;
    }

    public XMLFile(String xml_string) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder dombuilder = domfac.newDocumentBuilder();
        if (xml_string.indexOf("<")==-1){
            Document dom = dombuilder.newDocument();
            dom.setXmlVersion("1.0");
            this.xml_root = dom.createElement(xml_string);
            dom.appendChild(this.xml_root);
        }else{
            Document dom = dombuilder.parse(new ByteArrayInputStream(xml_string.getBytes("UTF-8")));
            this.xml_root = dom.getDocumentElement();
        }
    }

    public Document getDom(){
        return this.xml_root.getOwnerDocument();
    }

    public Element getMe(){
        return this.xml_root;
    }

    public String getText(){
        return this.xml_root.getTextContent();
    }

    public int getInteger(){
        return Integer.valueOf(xml_root.getTextContent());
    }

    public float getFloat(){
        return Float.valueOf(xml_root.getTextContent());
    }

    public double getDouble(){
        return Double.valueOf(xml_root.getTextContent());
    }

    public boolean getBoolean(){
        return "true".equalsIgnoreCase(getText());
    }

    public void setText(String text){
        this.xml_root.setTextContent(text);
    }

    public XMLFile[] gets(String node_name){
        XMLFile[] results = null;
        NodeList nl = this.xml_root.getElementsByTagName(node_name);
        if (nl!=null){
            results = new XMLFile[nl.getLength()];
            for (int i=0;i<nl.getLength();i++){
                results[i] = new XMLFile((Element)nl.item(i));
            }
        }
        return results;
    }

    public XMLFile getWithDefault(String node_name, String default_code){
        XMLFile result = this.get(node_name);
        if (result==null) try {
            result = new XMLFile(default_code);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public XMLFile get(String node_name){
        XMLFile[] r = this.gets(node_name);
        if (r.length>0){
            return r[0];
        }
        return null;
    }

    public XMLFile[] gets(String node_name, String[][] attr_values){
        XMLFile[] results = null;
        int sum = 0;
        try{
            XMLFile[] rs = this.gets(node_name);
            results = new XMLFile[rs.length];
            for (int i=0;i<rs.length;i++){
                NamedNodeMap attrs = rs[i].getMe().getAttributes();
                Boolean is_same = true;
                for (int att=0;att<attr_values.length;att++){
                    Node attr = attrs.getNamedItem(attr_values[att][0]);
                    if (attr==null || attr_values[att][1]==null || !attr.getTextContent().equals(attr_values[att][1])){
                        is_same = false;
                        break;
                    }
                }
                if (is_same==true){
                    results[sum] = rs[i];
                    sum++;
                }
            }
            return results;
        }
        catch(Exception e){
            return null;
        }
    }

    public XMLFile get(String node_name, String[][] attr_values){
        XMLFile[] rs = this.gets(node_name, attr_values);
        if (rs!=null && rs.length>0){
            return rs[0];
        }
        return null;
    }

    public XMLFile[] gets(String node_name, String attr_str){
        String[][] attr_values = this.dumpValues(attr_str);
        return this.gets(node_name, attr_values);
    }

    public XMLFile get(String node_name, String attr_str){
        XMLFile[] rs = this.gets(node_name, attr_str);
        if (rs!=null && rs.length>0){
            return rs[0];
        }
        return null;
    }

    public String[][] getStringTable(String row_name, String column_name){
        try{
            String[][] r = null;
            NodeList rows = this.xml_root.getElementsByTagName(row_name);
            int rows_len = rows.getLength();
            r = new String[rows_len][];
            for (int i=0;i<rows_len;i++){
                NodeList columns = ((Element)rows.item(i)).getElementsByTagName(column_name);
                int columns_len = columns.getLength();
                r[i] = new String[columns_len];
                for (int ii=0;ii<columns_len;ii++){
                    r[i][ii] = ((Element)columns.item(ii)).getTextContent();
                }
            }
            return r;
        }
        catch(Exception e){
            return null;
        }
    }

    public String[][] getStringTable(String rowName, String[] columnNames){
    	String[][] result = null;
    	XMLFile[] rows = gets(rowName);
    	if (rows.length > 0){
    		result = new String[rows.length][columnNames.length];
    		for (int i = 0; i < rows.length; i++){
    			XMLFile r = rows[i];
    			for (int h = 0; h < columnNames.length; h++){
    				XMLFile c = r.get(columnNames[h]);
    				String cs = null;
    				if (null != c){
    					cs = c.getText();
    				}
    				result[i][h] = cs;
    			}
    		}
    	}
    	return result;
    }


    public String toCode(){
        StringWriter strWtr = new StringWriter();
        StreamResult strResult = new StreamResult(strWtr);
        TransformerFactory tfac = TransformerFactory.newInstance();
        try{
            Transformer t = tfac.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform(new DOMSource(this.xml_root),strResult);
            return strResult.getWriter().toString();
        }
        catch(Exception e){
            return null;
        }
    }

    public XMLFile setAttr(String[][] attr_values){
        try{
            NamedNodeMap attrs = this.xml_root.getAttributes();
            for (int i=0;i<attr_values.length;i++){
                this.xml_root.setAttribute(attr_values[i][0], attr_values[i][1]);
            }
            return this;
        }
        catch(Exception e){
            return null;
        }
    }

    public XMLFile setAttr(String attr_str){
        return this.setAttr(this.dumpValues(attr_str));
    }

    public XMLFile addNode(String node_name, String node_value){
        Element new_node = this.getDom().createElement(node_name);
        new_node.setTextContent(node_value);
        Node nn = this.xml_root.appendChild((Node)new_node);
        return new XMLFile((Element)nn);
    }

    public XMLFile addNode(String node_name, String node_value, String[][] attr_values){
        return this.addNode(node_name, node_value).setAttr(attr_values);
    }

    public XMLFile addNode(String node_name, String node_value, String attr_str){
        return this.addNode(node_name, node_value).setAttr(attr_str);
    }

    public XMLFile addNode(XMLFile new_node){
        return this.addNode(new_node.getMe());
    }

    public XMLFile addNode(Node the_node){
        Node new_node = null;
        if (this.getDom()!=the_node.getOwnerDocument()){
            new_node = this.getDom().importNode(the_node, true);
            new_node = this.xml_root.appendChild(new_node);
        }
        return new XMLFile((Element)new_node);
    }

    public XMLFile addNode(String xml_code){
        if (xml_code.trim().startsWith("<")) {
            DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder dombuilder = domfac.newDocumentBuilder();
                Document new_dom = dombuilder.parse(new ByteArrayInputStream(xml_code.getBytes("UTF-8")));
                Node new_node = (Node)new_dom.getDocumentElement();
                new_node = this.xml_root.appendChild(this.xml_root.getOwnerDocument().importNode(new_node, true));
                return new XMLFile((Element)new_node);
            }
            catch(Exception e){
                return null;
            }
        } else {
            Element new_node = this.getDom().createElement(xml_code);
            Node nn = this.xml_root.appendChild((Node)new_node);
            return new XMLFile((Element)nn);
        }
    }
}
