/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserId;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.ProjectUserId;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;

/**
 *
 * @author jokerfvd
 */
public class Versioning implements IVersioning{
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();

    public String getRevisionUserName(String number) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getHeadRevision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String login(String projectName, String userName, String pass) {
        User user = userDAO.getUserByUserName(userName);
        Project project = projectDAO.getProjectByName(projectName);
        ProjectUserId pui = new ProjectUserId(user.getId(), project.getId());
        ProjectUser pu = projectUserDAO.getProjectUser(pui);
        if (pu.getToken().equals("")){
            pu.generateToken();
        }
        return pu.getToken();
    }
    
}
