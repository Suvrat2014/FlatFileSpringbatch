package springbatch1.flatfile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.batch.item.file.BufferedReaderFactory;
import org.springframework.core.io.Resource;

public class CustomBufferedReaderFactory implements BufferedReaderFactory  {

	@Override
	public BufferedReader create(Resource resource, String encoding) throws UnsupportedEncodingException, IOException {		
		ZipFile file = new ZipFile(resource.getFile());
		final Enumeration<? extends ZipEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            System.out.printf("File: %s Size %d  Modified on %TD %n", entry.getName(), entry.getSize(), new Date(entry.getTime()));
        //    extractEntry(entry, file.getInputStream(entry));
            System.out.println("Zip File Returned");
            return new BufferedReader(new InputStreamReader(file.getInputStream(entry), encoding));
        }		
		System.out.println("Returning Null. No files left");
		return null;
		
	//	return new BufferedReader(new InputStreamReader(new ZipInputStream(resource.getInputStream()), encoding));
		
//		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(resource.getFile()));
  //      ZipInputStream is = new ZipInputStream(bis);
		
	//	return new BufferedReader(is);
		
	//	return new BufferedReader(new InputStreamReader(new ZipInputStream(resource.getInputStream()), encoding));

		/*	ZipInputStream zis = new ZipInputStream(
		  new BufferedInputStream(
				  resource.getInputStream()));
		File target = new File("src/main/resources","abc");
		BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(target));
		zis.getNextEntry();
		  IOUtils.copy(zis,dest);
		  dest.flush();
		  dest.close();

return new BufferedReader(new FileReader(target)); */
		
		
	}

}
