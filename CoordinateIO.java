import java.io.*;
import java.util.*;

/**
 * Input/output for coordinates.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2025-2026
 */
public class CoordinateIO {
   
   // Each line in the text file must start with two integer values 
   // (x-coordinate and y-coordinate), separated by a space character. 
   // Some comments can be added as long as they are separated from 
   // the second integer by a space character.
   
   private File file;
   
   /**
    * Constructs an object for coordinate I/O. 
    */
   public CoordinateIO() {
   }
   
   /**
    * Constructs an object for coordinate I/O to a given text file. 
    * 
    * @param filename
    *           the name of the file
    */
   public CoordinateIO(String filename) {
      setFile(filename);
   }
   
   /**
    * Constructs an object for coordinate I/O to a given text file. 
    * 
    * @param file
    *           the file
    */
   public CoordinateIO(File file) {
      setFile(file);
   }
   
   /**
    * Gets the text file for coordinate I/O.
    * 
    * @return the file
    */
   public File getFile() {
      return file;
   }
   
   /**
    * Sets the text file for coordinate I/O.
    * 
    * @param filename
    *           the name of the file
    */
   public void setFile(String filename) {
      setFile(new File(filename));
   }
   
   /**
    * Sets the text file for coordinate I/O.
    * 
    * @param file
    *           the file
    */
   public void setFile(File file) {
      this.file = file;
   }

   /**
    * Reads the coordinates from the text file and puts them in a list.
    * 
    * @return a list of coordinates
    */
   public ArrayList<Coordinate> read() {
      try {
         if (file != null && file.canRead()) {
            return readStream(new FileReader(file));
         }
      } catch (IOException exc) {
         //
      }
      return new ArrayList<Coordinate>();
   }

   /**
    * Reads the coordinates from an input stream and puts them in a list.
    * 
    * @param in
    *           the input stream reader
    * @return a list of coordinates
    */
   protected ArrayList<Coordinate> readStream(Reader in) {
      ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
      try {
         coords.clear();
         BufferedReader reader = new BufferedReader(in);
         String line;
         while ((line = reader.readLine()) != null) {
            String[] coord = line.split("\\s+");
            if (coord.length >= 2) {
               try {
                  int x = Integer.parseInt(coord[0]);
                  int y = Integer.parseInt(coord[1]);
                  coords.add(new Coordinate(x, y));
               } catch (NumberFormatException exc) {
                  //
               }
            }
         }
         reader.close();
      } catch (IOException exc) {
         //
      }
      return coords;
   }

   /**
    * Writes a list of coordinates to the text file.
    * 
    * @param list
    *           the list of coordinates
    */
   public void write(ArrayList<Coordinate> list) {
      try {
         if (file != null) {
            file.createNewFile();
            if (file.canWrite()) {
               writeStream(new FileWriter(file), list);
            }
         }
      } catch (IOException exc) {
         //
      }
   }

   /**
    * Writes a list of coordinates to an output stream.
    * 
    * @param out
    *           the output stream writer
    * @param list
    *           the list of coordinates
    */
   protected void writeStream(Writer out, ArrayList<Coordinate> list) {
      PrintWriter writer = new PrintWriter(new BufferedWriter(out));
      for (Coordinate coord : list) {
         writer.println(coord.getX() + " " + coord.getY());
      }
      writer.close();
   }

}