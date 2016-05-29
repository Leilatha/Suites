package damson.suites.suites;

import java.io.Serializable;

/**
 * Created by Lfryeroc on 5/27/2016.
 * I'm not remaking this a third time
 */
public class PSAItem implements Serializable{
    private String author;
    private String date;
    private String text;
    private int id;

    public PSAItem(String name, String time, String content){
        author = name;
        date = time;
        text = content;
        id = -1;
    }

    public PSAItem(PSAItem post){
        author = post.getAuthor();
        date = post.getDate();
        text = post.getText();
        id = post.getId();
    }

    public String getAuthor(){
        return author;
    }

    public String getDate(){
        return date;
    }

    public String getText(){
        return text;
    }

    public int getId(){
        return id;
    }

    public void setId(int numbah){
        id = numbah;
    }
}
