/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.managedbean;

import com.mycompany.library.ejb.UsuarioEJB;
import com.mycompany.library.model.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    @EJB
    UsuarioEJB usuarioEJB;
    private Usuario usuario = new Usuario();
    
    private String login;
    private String senha;

    public void salvar() {
        usuarioEJB.salvar(this.usuario);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Usuário salvo com sucesso!", null));

        this.usuario = new Usuario();
    }

    public void excluir(Long id) {
        System.out.println("id: " + id);
        usuarioEJB.excluir(id);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Usuário excluído com sucesso!", null));
    }

    public Usuario editar(Usuario u) {
        this.usuario = u;
        return u;
    }

    public List<Usuario> todos() {
        return usuarioEJB.buscaTodos();
    }

    public String autentica() {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        List<Usuario> usuarios = usuarioEJB.buscaTodos();
        Usuario user = null;
        
        for (int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getLogin().equals(getLogin()) && 
                    usuarios.get(i).getSenha().equals(getSenha())) {
                user = usuarios.get(i);
            }
        }

        if (user != null) {
            
            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(false);
            session.setAttribute("usuario", user);
            
            if(user.getPermissao().compareTo("admin") == 0) {
                return "/admin/index.xhtml?faces-redirect=true";
            } else {
                return "/aluno/index.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Login e/ou senha inválidos!", null));

            return "/index";
        }
    }

    public String registraSaida() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        session.removeAttribute("usuario");

        return "/index";
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
