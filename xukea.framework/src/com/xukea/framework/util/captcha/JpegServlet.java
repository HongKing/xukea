package com.xukea.framework.util.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xukea.common.util.Config;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class JpegServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static int imgW = 120;            // 验证码图片宽(推荐比例: imH:imgW=1:(length+1) )
	private static int imgH = 30;             // 验证码图片高
	private static int disturb = 50;          // 背景干扰文字个数（useNoise=true时有用）
	private static boolean useNoise = false;  // 是否添加杂点
	private static boolean useCurve = true;   // 是否画混淆曲线
	private static boolean useWave  = false;  // 是否做正弦变换
	private static String strArray[] = null;  // 验证码字符
	private static int fontSize = 25;         // 验证码字体大小(px)
	private Color bcolor = null;       // 背景色
	private Color fcolor = null;       // 字体色
	private Font  font   = null;       // 字体
	private BufferedImage image = null;      // 验证码图片实例
	
	// 定义验证码字符。
	private static String digit[] = {"0","1","2","3","4","5","6","7","8","9"};
	private static String upper[] = {"A","B","C","D","E","F","G","H","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private static String lower[] = {"a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z",};
	private static String alnum[] = {
			 "A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
	         "a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z",
	         "1","2","3","4","5","6","7","8","9"};
	private static String alpha[] = {
		 "A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
         "a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
	
	private static String fontPath = "";     // 字体路径
	private static String fontNum  = "";     // 字体编号
	private static String sname    = "vnum"; // session名称
	private static int    length   = 4;      // 验证码位数
	static {
		if("weblogic".equalsIgnoreCase(Config.getInstance().getString("server.type"))){
			fontPath = Thread.currentThread().getContextClassLoader().getResource("/") + "ttfs/";
		}else{// tomcat
			fontPath = JpegServlet.class.getClassLoader().getResource("/") + "ttfs/";
		}
		fontPath = fontPath.substring( "file:".length() );
		fontNum  = Config.getInstance().getString("captcha.strfont");
		imgW     = Config.getInstance().getInt("captcha.width");
		imgH     = Config.getInstance().getInt("captcha.height");
		length   = Config.getInstance().getInt("captcha.length");
		disturb  = Config.getInstance().getInt("captcha.disturb");
		useNoise = Config.getInstance().getBoolean("captcha.noise");
		useCurve = Config.getInstance().getBoolean("captcha.curve");
		useWave  = Config.getInstance().getBoolean("captcha.wave");
		sname    = Config.getInstance().getString("captcha.sname");
		
		// 验证码字符
		String type = Config.getInstance().getString("captcha.strtype");
		if("digit".equalsIgnoreCase(type)){
			strArray = digit;
		}else if("upper".equalsIgnoreCase(type)){
			strArray = upper;
		}else if("lower".equalsIgnoreCase(type)){
			strArray = lower;
		}else if("alpha".equalsIgnoreCase(type)){
			strArray = alpha;
		}else{
			strArray = alnum;
		}
		// 初始化
		if(imgW==0){
			imgW = (int)(length * fontSize * 1.5 + fontSize * 1.5);
		}
		if(imgH==0){
			imgH = (int)(fontSize * 2);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		writeCaptchaImage(request, response);
	}
	
	public void writeCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		image = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
		bcolor = getRandColor(230, 255);
		fcolor = getRandColor(50, 150);
		//字体(7~9不适合加杂点)
		try{
			String fontFileName = "";
			if("auto".equals(fontNum)){
				int ftemp = useNoise ? randomInt(1, 7) : randomInt(1, 10);
				fontFileName = fontPath + ftemp + ".ttf";
			}else{
				fontFileName = fontPath + fontNum + ".ttf";
			}
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream( fontFileName ));
			font = font.deriveFont(Font.PLAIN, fontSize);
		}catch(Exception e){
			font = new Font("Times New Roman", Font.PLAIN, fontSize);
		}
		//生成随机码
		String randstr = "";
		String rands[] = new String[length];
		for(int i=0;i<length;i++){
			rands[i] = strArray[randomInt(0, strArray.length)];
			randstr += rands[i];
		}
		request.getSession().setAttribute(sname, randstr);
		
		Graphics2D g = image.createGraphics();
		g.setColor( bcolor );
		g.fillRect(0, 0, imgW, imgH);
		//消除锯齿
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//绘杂点
		if( useNoise ) g = writeNoise(g);
		//绘干扰线
		if( useCurve ) g = writeCurve(g);

		//绘验证码文字
		g.setColor(fcolor);
		g.setFont(font);
		float codeNXL = fontSize/2;      // 下一字符左边距
		float codeNXT = fontSize * 1.2f; // 下一字符上边距
		for (int i = 0; i < length; i++) {
			double a = randomInt(1, 3);
			double alpha = Math.PI/randomInt(8, 15);
			alpha = Math.pow(-1, a) * alpha;
			float px = (alpha>0) ? (codeNXL + fontSize/2) : codeNXL;
			float py = codeNXT;//(alpha<0) ? (codeNXT + fontSize) : codeNXT;
			
			g.rotate( -alpha, px, py); //旋转   
		    g.drawString(rands[i], px, py );//要旋转的文本   
			g.rotate(  alpha, px, py);  //转回来
	    	codeNXL += fontSize * (1 + 1/randomInt(3, 10));
	    	codeNXT  = fontSize * (1 + 1/randomInt(2, 7));
		}
		
		//正弦变换 TODO 有问题，建议先不用
		if( useWave ){
			shearX(g, imgW, imgH, bcolor);
			shearY(g, imgW, imgH, bcolor);
		}
		
		//输出图像
		//Solves on weblogic the response already committed question
		response.reset();
		//set no cache
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		response.setDateHeader("Expires", 0);
		//if deviant show ,use follow on the heels of code (now annotated)
		//ImageIO.write(image, "JPEG", response.getOutputStream());
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
		param.setQuality(1.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(image);
	}

	/**
	 * @描述:绘杂点
	 * 
	 * @参数：g 图像
	 * @返回值:
	 */
	private Graphics2D writeNoise(Graphics2D g){
		for(int i=0;i<disturb;i++){
			Color ncolor = new Color(randomInt(150,225), randomInt(150,225), randomInt(150,225));
			String str = strArray[randomInt(0, strArray.length)];
			int px = randomInt(0, imgW);
			int py = randomInt(0, imgH);
			g.setColor(ncolor);
			g.drawString(str, px, py);
		}
		return g;
	}

	/**
	 * @描述:绘干扰线
	 *       由两条连在一起构成的随机正弦函数曲线作干扰线(你可以改成更帅的曲线函数) 
     *      
	 *		正弦型函数解析式：y=Asin(ωx+φ)+b
	 *      各常数值对函数图像的影响：
	 *        A：决定峰值（即纵向拉伸压缩的倍数）
	 *        b：表示波形在Y轴的位置关系或纵向移动距离（上加下减）
	 *        φ：决定波形与X轴位置关系或横向移动距离（左加右减）
	 *        ω：决定周期（最小正周期T=2π/∣ω∣）
	 *
	 * @参数：g 图像
	 * @返回值:
	 */
	private Graphics2D writeCurve(Graphics2D g){
		g.setColor(fcolor);
		
		//前半段正弦干扰
		float A = randomInt(1, imgH/2);            // 振幅
		float b = randomInt(-imgH/4, imgH/4);      // Y轴方向偏移量
		float f = randomInt(-imgH/4, imgH/4);      // X轴方向偏移量
		float T = randomInt(imgH*1.5, imgW*2);     // 周期
		float w = (2 * (float)Math.PI) / T;
		
		int px1 = randomInt(1, imgH/2);            // 曲线横坐标起始位置
		int py1 = (int)(A * (float)Math.sin(w*px1 + f) + b + imgH/2);
		int pxEnd = randomInt(imgW/2, imgW*0.667f); // 曲线横坐标结束位置
		int px  = px1;
		int py  = py1;
		for(; px<=pxEnd; px=px+1){
			if(w==0) break;
			py = (int)(A * (float)Math.sin(w*px + f) + b + imgH/2); // y = Asin(ωx+φ) + b
			int i = randomInt(1, 3);
			while (i>0){
				g.drawLine(px1, py1, px+i, py+i);
				px1 = px+i;
				py1 = py+i;
				i--;
			}
		}

		//后半段正弦干扰
		A = randomInt(1, imgH/2);            // 振幅
		f = randomInt(-imgH/4, imgH/4);      // X轴方向偏移量
		T = randomInt(imgH*1.5, imgW*2);     // 周期
		w = (2 * (float)Math.PI) / T;
		b = (int)(py - A * (float)Math.sin(w*px + f) - imgH/2);      // Y轴方向偏移量
		
		pxEnd = imgW; // 曲线横坐标结束位置
		for(px=px1; px<=pxEnd; px=px+1){
			if(w==0) break;
			py = (int)(A * (float)Math.sin(w*px + f) + b + imgH/2); // y = Asin(ωx+φ) + b
			int i = randomInt(1, 3);
			while (i>0){
				g.drawLine(px1, py1, px+i ,py+i);
				px1 = px+i;
				py1 = py+i;
				i--;
			}
		}

		return g;
	}
	
	/**
	 * @描述:给定范围获得随机颜色
	 * 
	 * @参数：fc 起始值
	 * @参数：bc 结束值
	 * @返回值:Color
	 */
	private Color getRandColor(int fc, int bc){
		Random random = new Random();
		fc = (fc>255) ? 255 : ((fc<0) ? 0 : fc);
		bc = (bc>255) ? 255 : ((bc<0) ? 0 : bc);
		if(fc>bc){
			int t = fc; fc=bc; bc=t;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @描述:返回[from,to)之间的一个随机整数
	 * 
	 * @参数：from 起始值
	 * @参数：to 结束值
	 * @返回值:[from,to)之间的一个随机整数
	 */
	private int randomInt(double from, double to){
		Random r = new Random();
		int len = (int)Math.ceil(to-from);
		int str = (int)Math.floor(from);
		int temp = r.nextInt( Math.abs(len) );
		temp = (len<0) ? -temp : temp;
		return str + temp;
	}
	
	/**
	 * @描述:水平正弦变换
	 * 
	 * @参数: g  图像
	 * @参数: w1 图像宽
	 * @参数: h1 图像高
	 * @参数: color 背景色
	 * @返回值: 
	 */
	public void shearX(Graphics g, int w1, int h1, Color color) {    
	    int period = randomInt(1,5);    
	    boolean borderGap = true;    
		int frames = 1;    
		int phase = randomInt(1,5);
		
		for (int i = 0; i < h1; i++) {    
			double d = (double) (period >> 1)    
			     * Math.sin((double) i / (double) period    
			       + (6.2831853071795862D * (double) phase)    
			       / (double) frames);    
			g.copyArea(0, i, w1, 1, (int) d, 0);    
			if (borderGap) {    
				g.setColor(color);    
				g.drawLine((int) d, i, 0, i);    
				g.drawLine((int) d + w1, i, w1, i);    
			}    
		}    
	}

	/**
	 * @描述:垂直正弦变换
	 * 
	 * @参数: g  图像
	 * @参数: w1 图像宽
	 * @参数: h1 图像高
	 * @参数: color 背景色
	 * @返回值: 
	 */
	public void shearY(Graphics g, int w1, int h1, Color color) {   
		int period = randomInt(1,5);
		boolean borderGap = true;    
		int frames = 5;    
		int phase = 2;    
		for (int i = 0; i < w1; i++) {    
			double d = (double) (period >> 1)    
					* Math.sin((double) i / (double) period    
					+ (6.2831853071795862D * (double) phase)    
					/ (double) frames);    
			g.copyArea(i, 0, 1, h1, 0, (int) d);    
			if (borderGap) {    
				g.setColor(color);    
				g.drawLine(i, (int) d, i, 0);    
				g.drawLine(i, (int) d + h1, i, h1);    
			}
		}  
	}
}