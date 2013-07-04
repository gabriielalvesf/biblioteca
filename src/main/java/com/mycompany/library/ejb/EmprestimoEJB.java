/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.ejb;

import com.mycompany.library.model.Emprestimo;
import com.mycompany.library.model.Livro;
import com.mycompany.library.model.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class EmprestimoEJB {
    
    @PersistenceContext(name = "LibraryPU")
    EntityManager em;
    
    public void salvar(Emprestimo emprestimo) {
        em.merge(emprestimo);
    }
    
    public Usuario buscarUsuario(String login) {
        Query query = em.createQuery("select u from Usuario u");
        List<Usuario> usuarios = query.getResultList();
        Usuario user = null;
        
        for(int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getLogin().compareTo(login) == 0) {
                user = usuarios.get(i);
            }
        }
        
        return user;
    }
    
    public Livro buscarLivro(Long id) {
        return em.find(Livro.class, id);
    }
    
    public List<Emprestimo> todosEmprestados() {
        return em.createQuery("select e from Emprestimo e where e.devolvido = 0").getResultList();
    }
    
    public List<Emprestimo> todosDevolvidos() {
        return em.createQuery("select e from Emprestimo e where e.devolvido = 1").getResultList();
    }
    
    public void devolver(Emprestimo e) {
        Emprestimo emp = em.find(Emprestimo.class, e.getId());
        emp.setDevolvido(true);
        emp.setDataDevolucao(new Date());
        em.merge(emp);
    }
    
}
