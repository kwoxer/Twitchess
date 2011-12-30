package de.fhb.projects.Twitchess.image;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class GenerateImage implements ImageObserver {
	private int fieldDimension;
	private Point origin;
	private String boardFilename;
	private String figureFilenamePatter;

	public GenerateImage(String propertyFilename) throws HeadlessException {
		super();
		initialiseFromPropertyFile(propertyFilename);
	}

	private void initialiseFromPropertyFile(String propertyFilename) {
		try {
			int x, y;
			FileInputStream input = new FileInputStream(propertyFilename);
			Properties p = new Properties();

			p.load(input);

			setFieldDimension(Integer
					.parseInt((String) p.get("fieldDimension")));

			x = Integer.parseInt((String) p.get("origin_x"));
			y = Integer.parseInt((String) p.get("origin_y"));
			setOrigin(new Point(x, y));

			setBoardFilename((String) p.get("boardFilename"));
			setFigureFilenamePatter((String) p.get("figureFilenamePatter"));
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertyFilename);
		} catch (IOException e) {
			System.err
					.println("I/O failed. Could not initialise image generation!");
		} catch (NumberFormatException e) {
			System.err.println(propertyFilename
					+ " contains bad data. Setting default values...");
			setFieldDimension(55);
			setOrigin(new Point(52, 0));
		}
	}

	public BufferedImage generateImageFromFen(String fen) {
		BufferedImage backgroundImage = null;

		try {
			backgroundImage = resetImage();
			addFiguresToImage(fen, backgroundImage);

		} catch (IOException e) {
			backgroundImage = null;
		}

		return backgroundImage;
	}

	private void addFiguresToImage(String fen, BufferedImage backgroundImage) {
		char c;
		int row = 0, column = 0;

		for (int i = 0; ' ' != fen.charAt(i); i++) {
			c = fen.charAt(i);

			if (Character.isLetter(c)) {
				generateImage(getFigureFilename(c), row, column,
						backgroundImage);
				column++;
			} else if (Character.isDigit(c)) {
				column += c - '0';
			} else if (c == '/') {
				row++;
				column = 0;
			}

		}
	}

	public void generateImage(String filename, int row, int column,
			BufferedImage backgroundImage) {
		try {
			BufferedImage foregroundImage = ImageIO.read(new File(filename));
			backgroundImage.getGraphics().drawImage(foregroundImage,
					columnToCoordinate(column), rowToCoordinate(row), this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveImage(File file, BufferedImage bimg) {
		try {
			ImageIO.write(bimg, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFigureFilename(char c) {
		String result = figureFilenamePatter;

		result = result.replace("%c", Character.isUpperCase(c) ? "w" : "b");
		result = result.replace("%f", "" + Character.toLowerCase(c));

		return result;
	}

	public BufferedImage resetImage() throws IOException {

		BufferedImage backgroundImage;
		backgroundImage = ImageIO.read(new File(boardFilename));
		return backgroundImage;
	}

	public int columnToCoordinate(int column) {
		return origin.x + column * fieldDimension;
	}

	public int rowToCoordinate(int row) {
		return origin.y + row * fieldDimension;
	}

	public int getFieldDimension() {
		return fieldDimension;
	}

	public void setFieldDimension(int fieldDimension) {
		this.fieldDimension = fieldDimension;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public String getBoardFilename() {
		return boardFilename;
	}

	public void setBoardFilename(String boardFilename) {
		this.boardFilename = boardFilename;
	}

	public String getFigureFilenamePatter() {
		return figureFilenamePatter;
	}

	public void setFigureFilenamePatter(String figureFilenamePatter) {
		if (figureFilenamePatter == null
				|| !figureFilenamePatter.contains("%f")
				|| !figureFilenamePatter.contains("%c"))
			this.figureFilenamePatter = "%f-%c.png";
		else
			this.figureFilenamePatter = figureFilenamePatter;
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

}