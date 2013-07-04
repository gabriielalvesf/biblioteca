/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.managedbean;

import com.mycompany.library.ejb.LivroEJB;
import com.mycompany.library.model.Livro;
import com.mycompany.library.model.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean
@SessionScoped
public class LivroBean {

    @EJB
    LivroEJB livroEJB;
    private Livro livro = new Livro();

    public void salvar() {
        livroEJB.salvar(this.livro);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Livro salvo com sucesso!", null));

        this.livro = new Livro();
    }

    public void excluir(Long id) {
        livroEJB.excluir(id);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Livro excluído com sucesso!", null));
    }

    public Livro editar(Livro l) {
        this.livro = l;
        return l;
    }

    public List<Livro> todos() {
        return livroEJB.buscaTodos();
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}