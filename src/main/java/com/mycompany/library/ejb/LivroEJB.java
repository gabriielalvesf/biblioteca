/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.ejb;

import com.mycompany.library.model.Livro;
import com.mycompany.library.model.Usuario;
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
public class LivroEJB {
    
    @PersistenceContext(name="LibraryPU")
    EntityManager em;
    
    public void salvar(Livro livro) {
        em.merge(livro);
    }
    
    public void excluir(Long id) {
        Livro l = em.find(Livro.class, id);
        em.remove(l);
    }
    
    public List<Livro> buscaTodos() {
        Query query = em.createQuery("select l from Livro l order by l.nome asc");
        return query.getResultList();
    }
    
}
