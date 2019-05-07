package common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonServiceImpl implements CommonService {

	@Override
	public String upload(String category, MultipartFile file, HttpSession session) {
		//���ε��� ������ ��ġ
		//D:\Study_Spring\.metadata\.plugins\o..webapps\iot\resources
		String resources = session.getServletContext().getRealPath("resources");
		//D:\Study_Spring\.metadat..webapps\iot\resources\\upload
		String upload = resources + File.separator + "upload";
		
		//D:\Study_Spring\..iot\resources\\upload\\notice\\2019\\04\\05\\dffad34adfd-ffaf_abc.txt
		String folder = makeFolder(category, upload);
		
//		���� �ٹ������� ���� ����ڰ� ������ ���ϸ��� ���� �ٸ� ������
//		���ε��� �� �����Ƿ� ���ϸ��� ����id �� �ο��Ѵ�.
		//dffad34adfd-ffaf_abc.txt
		String uuid = UUID.randomUUID().toString() 
					+ "_" + file.getOriginalFilename();
		try {
			file.transferTo( new File(folder, uuid) );
		
		}catch(Exception e) {
		}
		return folder.substring(resources.length()) 
								+ File.separator + uuid;
	}

	private String makeFolder(String category, String upload) {
		//D:\Study_Spring\..iot\resources\\upload
		StringBuilder sb = new StringBuilder( upload );
		//D:\Study_Spring\..iot\resources\\upload\\notice
		sb.append( File.separator + category );
				
		//D:\Study_Spring\..oad\\notice\\2019
		Date now = new Date();
		sb.append( File.separator + 
					new SimpleDateFormat("yyyy").format(now) );
		//D:\Study_Spring\..oad\\notice\\2019\\04
		sb.append( File.separator + 
					new SimpleDateFormat("MM").format(now) );	
		//D:\Study_Spring\..oad\\notice\\2019\\04\\05
		sb.append( File.separator + 
					new SimpleDateFormat("dd").format(now) );
		upload = sb.toString();
		
		File f = new File( upload );
		if( ! f.exists() ) f.mkdirs();
		
		return upload;
	}

	@Override
	public File download(String filepath, String filename,
			HttpSession session, HttpServletResponse response) {

		//�ٿ�ε��ؿ� ������ �ִ� ������ ��ġ
		File file = new File( session.getServletContext()
					.getRealPath("resources") + filepath );
		String mime 
		= session.getServletContext().getMimeType(filename);
		if( mime == null )	mime = "application/octet-stream";
		response.setContentType(mime);
		
		try {
			//�ѱ����ϸ�ó��
			filename = URLEncoder.encode(filename, "utf-8");
			
			response.setHeader(
				"content-disposition", 
				"attachment; filename=" + filename);
			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();
		}catch(Exception e) {
		}
		return file;
	}

	@Override
	public void emailSend(String email, String name) {
		//�̸��ϼ����� �̸����ּ�, �����ڼ���
		basicEmail(email, name);
		
	}
	
	@Override
	public void emailSend(String email, String name, HttpSession session) {
//		attachEmail(email, name, session);	
		htmlEmail(email, name, session);	
	}
	
	private void htmlEmail(String email, String name, HttpSession session) {
		HtmlEmail mail = new HtmlEmail();
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
		mail.setFrom("ojink2@naver.com", "������");
		mail.addTo(email, name);
		
		mail.setSubject("Html �±� ���·� ������");
		StringBuilder sb = new StringBuilder();
		sb.append( "<html>" );
		sb.append( "<body>" );
		sb.append( "<a href='http://hanuledu.co.kr'>�ѿ� Ȩ ����</a>" );
		sb.append( "<img src='https://mvnrepository.com/assets/images/392dffac024b9632664e6f2c0cac6fe5-logo.png'/>" );
		sb.append("<h3>ȯ���մϴ�</h3>");
		sb.append("�����ۼ��ϱ�<br>");
		sb.append("�ϼ�!!");		
		sb.append( "</body>" );		
		sb.append( "</html>" );
		mail.setHtmlMsg( sb.toString() );
		
		EmailAttachment attach = new EmailAttachment();
		attach.setPath( session.getServletContext()
				.getRealPath("resources") 
				+ File.separator + "img"
				+ File.separator + "hanul.logo.png");
		mail.attach(attach);
	
		mail.send();
		
		}catch(Exception e) {
		}
		
	}
	
	private void attachEmail(
		String email, String name, HttpSession session) {
		
		MultiPartEmail mail = new MultiPartEmail();
		
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
		mail.setFrom("ojink2@naver.com", "������");
		mail.addTo(email, name);
		
		mail.setSubject("����÷�εǾ����ϴ�. Ȯ�ο��");
		mail.setMsg("÷�ε� ���� �� ���ŵǾ��� Ȯ���ϼ���~");
		
		//÷�����ϰ�ü 
		EmailAttachment attach = new EmailAttachment();
		attach.setPath( session.getServletContext()
				.getRealPath("resources") 
				+ File.separator + "img"
				+ File.separator + "hanul.logo.png");
		mail.attach(attach);
		
		attach = new EmailAttachment();
		attach.setURL( new URL("https://mvnrepository.com/assets/images/6a606fcf7b8526f25d1fc9b3fe8f39ad-growth.png") );
		mail.attach(attach);
		
		mail.send();
		
		}catch(Exception e) {
		}
		
	}
	
	
	private void basicEmail(String email, String name) {
		SimpleEmail mail = new SimpleEmail();
		
		//�̸��� �۽� ���� ����
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//���ϼ����� �α����ϱ� ���� ���̵�/��� ����
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
		mail.setFrom("ojink2@naver.com", "������");
		//������ ����
		mail.addTo(email, name); //����ȸ���� ����
		
		mail.setSubject("�ѿ� IoT ���� �Ա� ����");
		mail.setMsg("IoT ������ �Ʒ��� �����մϴ�!!");
		
		//��������
		mail.send();
		
		}catch(Exception e) {
		}
	}
	

}










