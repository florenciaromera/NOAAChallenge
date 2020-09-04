package ar.com.ada.api.noaa.usuarios;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @Column(name = "full_name")
    private String fullName;
    private String username;
    private String password;
    @Column(name = "e_mail")
    private String email;
    @CreationTimestamp
    @Column(name = "fecha_alta")
    private Date fechaAlta;
    @Column(name = "fecha_login")
    private Date fechaLogin;

    public Usuario() {

    }

    public Usuario(String fullName, String username, String password, String email, Date fechaLogin) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fechaLogin = fechaLogin;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }
}