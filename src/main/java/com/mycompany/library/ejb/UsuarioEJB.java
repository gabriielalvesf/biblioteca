/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.ejb;

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
public class UsuarioEJB {
    
    @PersistenceContext(name="LibraryPU")
    private EntityManager em;
    
    public void salvar(Usuario usuario) {
        em.merge(usuario);
    }
    
    public void excluir(Long id) {
        Usuario u = em.find(Usuario.class, id);
        em.remove(u);
    }
    
    public List<Usuario> buscaTodos() {
        Query query = em.createQuery("select u from Usuario u");
        return query.getResultList();
    }
    
    public List<Usuario> existeUsuario(String login, String senha) {
        Query query = em.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha");
        query.setParameter("login", login);
        query.setParameter("senha", senha);
        return query.getResultList();
        //return !query.getResultList().isEmpty();
        
    }
    
    public Usuario buscaUsuario(String login) {
        return em.find(Usuario.class, login);
    }
    
}
