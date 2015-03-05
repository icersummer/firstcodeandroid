package com.vjia.bookcollector.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.util.Log;

import com.vjia.bookcollector.pojo.BookEntity;

/**
 * write out the specific information to disk ( Android storage ) .<br/>
 * 1) for a Book's info, given a ISBN number, then should generate a file named
 * ISBN_number.properties in folder %root%\books_info\ISBN_number.prop; content
 * like : title: xxx, isbn: xxx
 * 
 * 2) also generate a file named books_list.properties in folder
 * %root%\books_info\, it contains which books are already in current folder,
 * with format: ISBN_number1=book title ISBN_number2=book title ...
 * 
 */
public class CsvWriterUtil {


	private static final String CLASSNAME = CsvWriterUtil.class.getName();
	
	/**
	* Enumeration for permissions.
	* <p>
	*/
	private static enum ReadPermission {
		VIEW,
		CREATE,
		EDIT,
		DELETE
	}



	public static String createAndPersisteFile(BookEntity book, Activity activity) {

		if (book == null)
			return null;

		String fileName = book.getIsbn13() + ".csv";
		boolean fileExisted = isExistedInAppFiles(fileName, activity);
		if (fileExisted)
			Log.d(CLASSNAME, "File is already existed : " + fileName );
		
		String fileContent = getFileContentInPropertiesFormat(book);
        try {  
            /* �����û��ṩ���ļ������Լ��ļ���Ӧ��ģʽ����һ�������.�ļ�����ϵͳ��Ϊ�㴴��һ���ģ� 
             * ����Ϊʲô����ط�����FileNotFoundException�׳�����Ҳ�Ƚ����ơ���Context������������� 
             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
             *   throws FileNotFoundException; 
             * openFileOutput(String name, int mode); 
             * ��һ�������������ļ����ƣ�ע��������ļ����Ʋ��ܰ����κε�/����/���ַָ�����ֻ�����ļ��� 
             *          ���ļ��ᱻ������/data/data/Ӧ������/files/chenzheng_java.txt 
             * �ڶ��������������ļ��Ĳ���ģʽ 
             *          MODE_PRIVATE ˽�У�ֻ�ܴ�������Ӧ�÷��ʣ� �ظ�д��ʱ���ļ����� 
             *          MODE_APPEND  ˽��   �ظ�д��ʱ�����ļ���ĩβ����׷�ӣ������Ǹ��ǵ�ԭ�����ļ� 
             *          MODE_WORLD_READABLE ����  �ɶ� 
             *          MODE_WORLD_WRITEABLE ���� �ɶ�д 
             *  */  
            FileOutputStream outputStream = activity.openFileOutput(fileName,  
                    Activity.MODE_PRIVATE);  
            outputStream.write(fileContent.getBytes());  
            outputStream.flush();  
            outputStream.close();  
            Log.d(CLASSNAME, "SAVE DONE.");
//            Toast.makeText(this, "����ɹ�", Toast.LENGTH_LONG).show();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return fileName;
		
	}
	
	/**
	 * check if the file with fileName is already existing
	 * @param fileName
	 * @param activity 
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static boolean isExistedInAppFiles(String fileName, Activity activity){
		try {
			FileInputStream inputStream = activity.openFileInput(fileName);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.w(CLASSNAME, e);
			return false;
		}
	}

	/**
	 * return the generated file_name, name it as *.csv (comma separated values) <br/>
	 * ex.
	 * title1,titl2,title3,title4 \n
	 * v1,v2,v3,v4 \n
	 * v11,v22,v33,v44
	 * 
	 * @param book
	 * @return
	 */
	public static String createAndPersisteFile_(BookEntity book) {
		if (book == null)
			return null;

		String fileName = book.getIsbn13() + ".csv";
		String fileContent = getFileContentInPropertiesFormat(book);

		boolean created = createFile(fileName, fileContent);

		if (created) {
			System.out.println( "book file created : " + fileName);
		} else {
			System.out.println("!! error occur, check logs : " + fileName);
		}

		return fileName;
	}

	private static boolean createFile(String fileName, String fileContent) {
		// TODO Auto-generated method stub
		String bashFilePath = "";
		String currentFilePath = bashFilePath + "\\books_info\\" + fileName;
		System.out.println( "currentFilePath ="+currentFilePath);
		File file = new File(currentFilePath);
		currentFilePath = file.getAbsolutePath();
		System.out.println( "fullpath ="+file.getAbsolutePath());
		if (file.exists()) {
			// report error, and return false
			System.out.println( "erro : file exist already : " + currentFilePath);
			return false;
		}

		try {
//			boolean created = file.createNewFile();
			createFolderAndFile(currentFilePath);
			// append content
			FileWriter fwriter = new FileWriter(currentFilePath);
			fwriter.append(fileContent);
			fwriter.flush();
			fwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private static void createFolderAndFile(final String absolutePath) throws IOException {
		// TODO Auto-generated method stub
		String folderPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
		File folder = new File(folderPath);
		if(!folder.exists()) {
			boolean dirCreated = folder.mkdirs();			
		}
		File file = new File(absolutePath);
		boolean fileCreated = file.createNewFile();		
	}

	/**
	 * Reflect to load all attributes' values.
	 * 
	 * construct CSV format string.
	 * 
	 * @param book
	 * @return
	 */
	private static String getFileContentInPropertiesFormat(BookEntity book) {
		// TODO Auto-generated method stub
		StringBuffer bookInfo = new StringBuffer();
//		bookInfo.append("title1,title2,title3,title4\n");
		String headerLine = "";
		String bodyLine = "";
		
		Field[] fileds = BookEntity.class.getDeclaredFields();
		for (Field field : fileds) {
			boolean initAccessible = field.isAccessible();
			String fieldName = field.getName();
			try {
				field.setAccessible(true);
				Object value = field.get(book);
				// clazz can be : string, date, int, double, float
				// TODO to cast them
				Class clazz = field.getDeclaringClass();

				headerLine += fieldName.toUpperCase() + ",";
//				if(value != null) {
					// value may be in format "value1, value2", e.g. the author tag; so replace comma ',' with '**'
					String strValue = String.valueOf(value);
					if(value != null) {
						strValue = strValue.replaceAll(",", "**");
					}
					bodyLine += strValue + ",";			
//				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				field.setAccessible(initAccessible);
			}
		}
		bookInfo.append(headerLine).append("\n").append(bodyLine).append("\n");

		return bookInfo.toString();
	}
	
	/**
	 * TEST CASE MAIN METHOD
	 * @param args
	 */
	public static void main(String[] args) {
		BookEntity book = new BookEntity();
		book.setAuthor("Mark Twin");
		book.setIsbn13("909090909765");
		book.setLink("http://google.com/vj");
		book.setPrice("$100");
		
		String fileName = createAndPersisteFile(book, null);
		System.out.println(fileName);
	}
}
