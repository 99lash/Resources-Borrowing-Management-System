package view;
import model.UserModel;

public class UserView {
  
  UserModel model;

  public void view() {
    System.out.println(model.getUsername());
  }

}
