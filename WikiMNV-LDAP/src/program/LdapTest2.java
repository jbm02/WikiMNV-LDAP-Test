package program;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapTest2 {

	public static void main(String[] args) {

		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=manager,dc=wikimnv,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, "secret");

		try {
			// Create initial context
			DirContext ctx = new InitialDirContext(env);
			
			// Create the default search controls
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			// Specify the search filter to match
			// Ask for objects that have the attribute "uid" == "abdali"
			String filter = "uid=abdali";

			// Search for objects that have those matching attributes
			NamingEnumeration<SearchResult> answer = ctx.search("dc=wikimnv,dc=com", filter, ctls);

			while (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				System.out.println(">>>" + sr.getName());
				System.out.println(sr.getAttributes());
			}
			
		    // Close the context when we're done
		    ctx.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}
