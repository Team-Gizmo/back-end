package gizmo.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

// utility methods NOT found in apache.commons.* or elsewhere

public class Util {

	public static String escapeQuotes(String s) {
    if (s == null) {
      return null;
    }
    int n = s.indexOf("'");
    while (n != -1) {
      s = s.substring(0, n) + "'" + s.substring(n);
      n = s.indexOf("'", n + 2);
    }
    return s;
  }
	
	public static String addCharacter(String input, char ch) {
		StringBuilder sb = new StringBuilder(input);
		int position = input.indexOf(ch);
		sb.insert(position+1, ch);
		return sb.toString();
	}
	
	public static String escapeNewlines(String s) {
    if (s == null) {
      return null;
    }
    int n = s.indexOf("\n");
    while (n != -1) {
      s = s.substring(0, n) + "'" + s.substring(n);
      n = s.indexOf("'", n + 2);
    }
    return s;
  }
	
	public static String escapePercent(String s) {
    if (s == null) {
      return null;
    }
    int n = s.indexOf("%");
    while (n != -1) {
      s = s.substring(0, n) + "'" + s.substring(n);
      n = s.indexOf("'", n + 2);
    }
    return s;
  }
	
	public static String escapeComma(String s) {
    if (s == null) {
      return null;
    }
    int n = s.indexOf(",");
    while (n != -1) {
      s = s.substring(0, n) + "'" + s.substring(n);
      n = s.indexOf("'", n + 2);
    }
    return s;
  }
	
	public static List<String> removeEmptyValues(List<String> inputValues) {
		List<String> values = new ArrayList<String>();
		for (String s : inputValues) {
			if (StringUtils.isNotBlank(s)) {
				values.add(s.toUpperCase());
			}
		}
		return values;
	}
	
	 // converts a Listing e.g. A,B,C,D to ('A','B','C','D') for use in an 'IN' sql clause
	 public static String convertStringListToTableIn(List<String> values) {
	   if (values != null && values.size() > 0) {
	     StringBuilder sb = new StringBuilder();
	     sb.append("(");
	     for (int i = 0, j = values.size(); i < j; i++) {
	       String value = values.get(i);
	       value = value.trim();
	       if (i == 0) {
	         sb.append("'"+value);
	       }
	       else {
	         sb.append(value);
	       }
	       sb.append(i != j-1 ? "','" : "'");
	     }
	     sb.append(")");
	     String results = sb.toString();
	     if (results.trim().length() == 0) {
	       return "";
	     }
	     else {
	       return results;
	     }
	   }
	   else {
	     return "";
	   }  
	 }
	 
	 // converts a Long Listing e.g. 1,4,2,8 to (1,4,2,8) for use in an 'IN' sql clause
	 public static String convertLongListToTableIn(List<Long> values) {
	   if (values.size() > 0) {
	     StringBuilder sb = new StringBuilder();
	     sb.append("(");
	     for (int i = 0, j = values.size(); i < j; i++) {
	    	 sb.append(values.get(i));
	       sb.append(i != j-1 ? "," : "");
	     }
	     sb.append(")");
	     String results = sb.toString();
	     if (results.trim().length() == 0) {
	       return "";
	     }
	     else {
	       return results;
	     }
	   }
	   else {
	     return "";
	   }  
	 }
	 
	 public static String printThrowable (final Throwable t) {
		 StringWriter out = new StringWriter();
		 t.printStackTrace(new PrintWriter(out));
		 return out.toString();
	 }
	 
	 // padding on both sides so the String remains centered in an html cell
	 public static String padToCenter(String s, int length) {
		 if (s != null) {
			 s = s.trim();
			 int sLength = s.length();
			 int difference = length - sLength;
			 if (difference > 1) {
				 StringBuilder sb = new StringBuilder();
				 int half = difference / 2;
				 for (int i = 0, j = half; i < j; i++) {
					 sb.append("&nbsp;");
				 }
				 sb.append(s);
				 for (int i = 0, j = half; i < j; i++) {
					 sb.append("&nbsp;");
				 }
				 return sb.toString();
			 }
			 if (difference == 1) {
				 return s;
			 }
			 else {
				 try {
					 String v = s.substring(0,length);
					 return v;
				 }
				 catch(Exception e) {
					 return s;
				 }
			 }
		 }
		 return "";
	 }
   
	 public static String getCurrentDate(String format) {
		 return new SimpleDateFormat(format).format(new Date());
	 }
	 
	 public static String formatDoubleValue(Double value, String format) {
		 DecimalFormat df = new DecimalFormat(format);
		 return df.format(value);
	 }
	 
	 // for Objects that implement Serializable
	 public static int getSizeOfSerializedObject(Object obj) {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     try {
    	 ObjectOutputStream oos = new ObjectOutputStream(baos);
    	 oos.writeObject(obj);
    	 oos.flush();
    	 oos.close();
     }
     catch (IOException e) {
    	 e.printStackTrace();
     }
     catch (Exception e) {
    	 e.printStackTrace();
     }
     return baos.toByteArray().length;
	 }

	 public static SOAPMessage createSOAPMessage(String xml) throws SOAPException, IOException {
	    MessageFactory factory = MessageFactory.newInstance();
	    SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
	    return message;
	 }
	 
	 public static void writeToFile(String fileName, List<String> list) {
		 try {
			 FileWriter writer = new FileWriter(fileName); 
			 for (String str: list) {
				 writer.write(str);
				 writer.write("\n");
			 }
			 writer.flush();
			 writer.close();
		 }
		 catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public static void writeBigDecimalToFile(String fileName, List<BigDecimal> list) {
		 try {
			 FileWriter writer = new FileWriter(fileName); 
			 for (BigDecimal bd: list) {
				 writer.write(bd.toString());
				 writer.write("\n");
			 }
			 writer.flush();
			 writer.close();
		 }
		 catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public static void writeToFile(String fileName, String s) {
		 File file = new File(fileName);
		 try {
			 file.createNewFile();
			 FileWriter writer = new FileWriter(file); 
		   writer.write(s); 
		   writer.flush();
		   writer.close();
		 }
		 catch (IOException e) {
			 e.printStackTrace();
		 }  
	 }
	 
	 public static List<String> getFileContentsAsList(String file) {
		 List<String> values = new ArrayList<>();
		 try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       values.add(line);
		    }
		 }
		 catch (IOException ioe) {
			 System.err.println(ioe);
		 }
		 return values;
	 }
	 
	 public static String convertSoapMessageToString(final SOAPMessage soapMessage) {
		 if (soapMessage == null) {
			 return null;
		 }
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		 try {
			 Transformer transformer = transformerFactory.newTransformer();
			 //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			 //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			 Source soapContent = soapMessage.getSOAPPart().getContent();
			 ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			 StreamResult result = new StreamResult(streamOut);
			 transformer.transform(soapContent, result);
			 return streamOut.toString();
		 }
		 catch (SOAPException | TransformerException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
		
	 public static String convertSoapBodyToString(final SOAPBody body) {
		 if (body == null) {
			 return "";
		 }
		 DOMSource source = new DOMSource(body);
		 StringWriter sw = new StringWriter();
		 try {
			 TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(sw));
		 }
		 catch (TransformerException | TransformerFactoryConfigurationError e) {
			 e.printStackTrace();
		 }
		 return sw.toString();
	 }
	 
	 public static XMLStreamReader getStreamReaderFromSoapBody(final SOAPBody body, String startTag) {
		 XMLStreamReader xsr = null;
     String xmlContent = convertSoapBodyToString(body);
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
     Reader reader = new StringReader(xmlContent);
     try {
    	 xsr = inputFactory.createXMLStreamReader(reader);
    	 xsr.nextTag();
  		 while (!xsr.getLocalName().equals(startTag)) {
  			 xsr.nextTag();
       }
     }
     catch (XMLStreamException e) {
    	 e.printStackTrace();
     }
     return xsr;
	 }
	 
	 //public static Class[] SOAP_CLASSES = (new Class[] {Object.class,String.class});
  
	 // outputs the xml from an annotated object(s)
	 public static String getXML(Object obj, Class<?>... classesToBeBound) {
		 Marshaller m = getMarshaller(classesToBeBound);
		 if (m != null && obj != null) {
			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 try {
				 m.marshal(obj,bos);
			 }
			 catch (JAXBException ex) {
				 ex.printStackTrace();
			 }
			 return bos.toString();
		 }
		 return "Could not extract xml";
	 }
  
	 private static Marshaller getMarshaller(Class<?>... classesToBeBound) {
		 Marshaller m = null;
		 try {
			 JAXBContext context = JAXBContext.newInstance(classesToBeBound);
			 m = context.createMarshaller();
			 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			 return m;
		 }
		 catch (JAXBException e) {
			 e.printStackTrace();
		 }
		 return m;
	 }

	 // validate input against the annotated entity
	 // throws exception and returns false if unable to perform the xml to java binding
	 @SuppressWarnings("unchecked")
	 public static <T> boolean inputIsValid(byte[] input, Class<T> clazz, Class<?>... classesToBeBound) {
		 Unmarshaller um = getUnmarshaller(classesToBeBound);
		 ByteArrayInputStream bais = new ByteArrayInputStream(input);
		 T obj = (T) getInstance(clazz);
		 if (obj != null) {
			 try {
				 obj = (T) um.unmarshal(bais);
			 }
			 catch (JAXBException e) {
				 e.printStackTrace();
				 return false;
			 }
		 }
		 return true;
	 }
  
	 // get the content tree from the inputstream's XML
	 @SuppressWarnings("unchecked")
	 public static <T> T getObjectFromInput(byte[] input, Class<T> clazz, Class<?>... classesToBeBound) throws JAXBException {
		 Unmarshaller um = getUnmarshaller(classesToBeBound);
		 ByteArrayInputStream bais = new ByteArrayInputStream(input);
		 T obj = (T) getInstance(clazz);
		 if (obj != null) {
			 obj = (T)um.unmarshal(bais);
			 return obj;
		 }
		 return null;
	 }
  
	 public static <T> T getInstance(Class<T> clazz) {
		 T obj = null;
		 try {
			 obj =  clazz.newInstance();
		 }
		 catch (InstantiationException | IllegalAccessException e) {
			 e.printStackTrace();
		 }
		 return obj;
	 }
  
	 private static <T> Unmarshaller getUnmarshaller(Class<?>... classesToBeBound) {
		 try {
			 JAXBContext context = JAXBContext.newInstance(classesToBeBound);
			 Unmarshaller um = context.createUnmarshaller();
			 return um;
		 }
		 catch (JAXBException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 
	 public static String base16Encode(int i) throws Exception {
		 return Integer.toHexString(i);
	 }
	 
	 public static String binaryValueForUserLockedFlag(int userLocked){
     String hex = Integer.toBinaryString(userLocked);
     while (hex.length() % 3 != 0) {
         hex = "0" + hex;
     }
     return hex;
	 }
	 
	 public static String decodeFlags(String pHexadecimalStr, int radix) {
		 String binaryVal = Integer.toBinaryString(Integer.parseInt(pHexadecimalStr,radix));
		 while (binaryVal.length() % radix != 0) {
       binaryVal = "0" + binaryVal;
		 }
		 return binaryVal;
	 }
	 
	 public static int checkUserLockedDecode(char a, char b, char c) {
     char[] array = {a, b, c};
     String pHexadecimalStr = String.valueOf(array);
     if (null != pHexadecimalStr && !pHexadecimalStr.isEmpty()) {
         while (pHexadecimalStr.length() % 4 != 0) {
             pHexadecimalStr = "0" + pHexadecimalStr;
         }
     }
     int value = Integer.parseInt(pHexadecimalStr,2);
     return value;
	 }
	 
	 public static List<List<String>> chopIntoParts(List<String> list, final int L) {
	    List<List<String>> parts = new ArrayList<>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	 }
	 
	 public static List<List<String>> createEqualSizeSubLists(List<String> list, final int numberElementsPerList) {
		 List<List<String>> parts = new ArrayList<>();
		 for (int start = 0; start < list.size(); start += numberElementsPerList) {
			 int end = Math.min(start + numberElementsPerList, list.size());
			 List<String> sublist = list.subList(start, end);
			 parts.add(sublist);
	   }
		 return parts;
	 }
	 
	 public static int refreshUeids(Collection<String> ueids) {
     if (CollectionUtils.isEmpty(ueids)) {
         return -1;
     }
     Set<String> ueidsSet = ueids
             .stream()
             .collect(Collectors.toSet());
     Collection<String> usersToBeProcessed = new ArrayList<>();
     for (String ueid : ueidsSet) {
    	 usersToBeProcessed.add(ueid);
     }
     for (String s : usersToBeProcessed) {
    	 System.err.println(s);
     }
     return usersToBeProcessed.size();
	 }
	 
	 public static List<String> convertStringToList(String s, String delimiter) {
		 return Collections
				 .list(new StringTokenizer(s,delimiter))
				 .stream()
				 .filter(e -> e != null)
		     .map(e -> String.valueOf(e))
		     .collect(Collectors.toList());
	 }
	 
	 public static void main (String[] args) {
		 String input = "O'NEIL";
		 char c = '\'';
		 String output = Util.addCharacter(input, c);
		 System.err.println(output);
	 }
	 
}