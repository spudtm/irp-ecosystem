package uk.ac.dotrural.irp.ecosystem.models.jaxb.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User
{
  private String userUri;
  private String nickname;
  private String email;
  private String password;
  private String authenticationToken;
  private boolean exists;
  
  public User()
  {}
  
  public User(String userUri)
  {
    this.userUri = userUri;
    this.exists = true;
  }
  
  public User(boolean exists)
  {
    this.exists = exists;
  }
  
  @XmlElement(name="nickname")
  public String getNickname()
  {
    return nickname;
  }
  
  public void setNickname(String nickname)
  {
    this.nickname = nickname;
  }
  
  @XmlElement(name="email")
  public String getEmail()
  {
    return email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  @XmlElement(name="password")
  public String getPassword()
  {
    return password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }

  @XmlElement(name="userUri")
  public String getUserUri()
  {
    return userUri;
  }

  public void setUserUri(String userUri)
  {
    this.userUri = userUri;
  }

  @XmlElement(name="authenticationToken")
  public String getAuthenticationToken()
  {
    return authenticationToken;
  }

  public void setAuthenticationToken(String authenticationToken)
  {
    this.authenticationToken = authenticationToken;
  }

  @XmlElement(name="exists")
  public boolean getExists()
  {
    return exists;
  }

  public void setExists(boolean exists)
  {
    this.exists = exists;
  }
}
