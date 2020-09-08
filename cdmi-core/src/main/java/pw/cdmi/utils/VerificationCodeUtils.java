package pw.cdmi.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/************************************************************
 * 生成验证码的字符串和图片流
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-6-12
 ************************************************************/
public class VerificationCodeUtils {

	/**
	 * 生成验证码主方法
	 * list.get(0)可获得验证码的(String)内容
	 */
	public static List<Object> produceCode() throws Exception {
		String s = "";
		BufferedImage image = new BufferedImage(75, 22, BufferedImage.TYPE_INT_RGB);
		Random r = new Random();
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 75, 22);
		char[] c = generateString();

		for (int i = 0; i < c.length; i++) {
			g.setColor(new Color(r.nextInt(200), r.nextInt(200), r.nextInt(200)));
			String str = c[i] + "";
			g.setFont(new Font("Serif", Font.BOLD, 24));
			g.drawString(str, 2 + (i * 17), 16);
		}
		g.setColor(Color.BLACK);

		for (int i = 0; i < 4; i++) {
			g.drawLine(r.nextInt(75), r.nextInt(22), r.nextInt(75), r.nextInt(22));
		}

		for (int i = 0; i < c.length; i++) {
			s += c[i];
		}

		List<Object> list = new ArrayList<Object>();
		list.add(s);
		list.add(formatImage(image));
		return list;
	}

	/**
	 * 生成验证码内容
	 */
	public static char[] generateString() {
		char[] pool = new char[62];
		int j = 0, m = 0;
		for (int i = 48; i < 123; i++) {
			if (57 < i && i < 65 || 90 < i && i < 97)
				continue;
			pool[j++] = (char) i;
			if (j == 62)
				break;
		}
		char[] jinzhi = new char[] { '1', '0', 'o', 'O', '2', 'Z', '9', 'g', 'l', 'L', 'I' };
		Random r = new Random();
		boolean[] used = new boolean[pool.length];
		char[] select = new char[4];
		int x = 0;
		while (true) {
			int index = r.nextInt(pool.length);
			for (int i = 0; i < jinzhi.length; i++) {
				if (jinzhi[i] == pool[index]) {
					x = 1;
					break;
				} else
					x = 0;
			}
			if (x == 1) {
				continue;
			}
			if (used[index] == true) {
				continue;
			} else {
				select[m++] = pool[index];
				used[index] = true;
			}
			if (m == select.length) {
				break;
			}
		}
		return select;
	}

	/**
	 * 生成验证码图片流
	 */
	public static byte[] formatImage(BufferedImage image) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpeg", bos);
			byte[] bytes = bos.toByteArray();
			// imageStream = new ByteArrayInputStream(bytes);
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
