package services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import entities.User;


@SuppressWarnings("unchecked")
@Path("/user")
@Consumes("application/json")
@Produces("application/json")

public class UserService extends BaseService {
	
	@GET
	public Response getUsers()  {
		EntityManager em = getEntityManager();
		List<User> users =  em.createQuery("select u from User u").getResultList();
		em.close();
		return Response.ok(users).build();
	}
	
	@Path("{id}")
	@GET
	public Response getUser(@PathParam("id") Long id)  {
		EntityManager em = getEntityManager();
		User user = null;
		try {
		 user = (User) em.createQuery("select u from User u where u.id = :id").
		 setParameter("id", id).getSingleResult();
		}catch(Exception e){}
		em.close();
		return (user == null) ?
	        Response.status(Response.Status.NOT_FOUND).entity("User not found for id : " + id).build() : 
	        Response.ok(user).build();
	}
	
	@POST
	public Response createUser(User user) {
		EntityManager em = getEntityManager();
		boolean saved = false;
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			saved = true;
		}catch(Exception e) {}
		em.close();
		return (!saved) ?
		        Response.status(Response.Status.NOT_ACCEPTABLE).entity(user).build() : 
		        Response.ok(user).build();
	}
	
	@PUT
	public Response updateUser(User user) {
		EntityManager em = getEntityManager();
		int update = 0;
		try {
			em.getTransaction().begin();
			update = em.createQuery("update from User u set u.firstName = :firstName, u.lastName = :lastName where u.id = :id")
			.setParameter("firstName", user.getFirstName()).
			setParameter("lastName", user.getLastName()).
			setParameter("id", user.getId()).executeUpdate();
			em.getTransaction().commit();
		}catch(Exception e){}
		em.close();
		return (update==0) ?
		        Response.status(Response.Status.NOT_ACCEPTABLE).entity(user).build() : 
		        Response.ok(user).build();
		}
	
	@Path("{id}")
	@DELETE
	public Response deleteUser(@PathParam("id") Long id) {
		EntityManager em = getEntityManager();
		int update = 0;
		try {
			em.getTransaction().begin();
			update = em.createQuery("delete from User u where u.id = :id")
			.setParameter("id", id).executeUpdate();
			em.getTransaction().commit();
		}catch(Exception e){}
		em.close();
		return (update==0) ?
		        Response.status(Response.Status.NOT_FOUND).entity("User not found for id : " + id).build() : 
		        Response.status(Response.Status.OK).entity("User deleted for id : " + id).build();
	}
	
}
