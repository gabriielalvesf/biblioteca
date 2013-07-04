/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.managedbean;

import com.mycompany.library.ejb.EmprestimoEJB;
import com.mycompany.library.model.Emprestimo;
import com.mycompany.library.model.Livro;
import com.mycompany.library.model.Usuario;
import java.util.ArrayList;
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
public class EmprestimoBean {
    
    private Long idSelecionado;
    private String login;
    private Usuario usuario = new Usuario();
    private Emprestimo emprestimo = new Emprestimo();
    private Emprestimo emprestimoSelecionado = new Emprestimo();
    private List<Livro> livros = new ArrayList<Livro>();
    
    @EJB
    EmprestimoEJB emprestimoEJB;
    
    public void salvar() {
        emprestimo.setUsuario(buscarUsuario());
        emprestimo.setLivros(livros);
        emprestimoEJB.salvar(emprestimo);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Emprestimo realizado com sucesso!", null));
        
        this.usuario = new Usuario();
    }
    
    public Usuario buscarUsuario() {
        return emprestimoEJB.buscarUsuario(login);
    }
    
    public void addLivro() {
        Livro l = emprestimoEJB.buscarLivro(idSelecionado);
        livros.add(l);
        showList();
    }
    
    public void showList() {
        for(int i = 0; i < livros.size(); i++) {
            System.out.println("Nome do livro: " + livros.get(i).getNome());
        }
    }
    
    public List<Emprestimo> todosEmprestados() {
        return emprestimoEJB.todosEmprestados();
    }
    
    public List<Emprestimo> todosDevolvidos() {
        return emprestimoEJB.todosDevolvidos();
    }
    
    public void devolver() {
        emprestimoEJB.devolver(emprestimoSelecionado);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Devolução realizada com sucesso!", null));
    }

    
    
    
    /*****************************
     * 
     * 
     */

    public Long getIdSelecionado() {
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Emprestimo getEmprestimoSelecionado() {
        return emprestimoSelecionado;
    }

    public void setEmprestimoSelecionado(Emprestimo emprestimoSelecionado) {
        this.emprestimoSelecionado = emprestimoSelecionado;
    }
    
}
