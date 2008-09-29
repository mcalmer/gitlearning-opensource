/**
 * Copyright (c) 2008 Red Hat, Inc.
 *
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 * 
 * Red Hat trademarks are not licensed under GPLv2. No permission is
 * granted to use or replicate Red Hat trademarks that are incorporated
 * in this software or its documentation. 
 */
package com.redhat.rhn.frontend.action.channel;

import com.redhat.rhn.common.db.datasource.DataResult;
import com.redhat.rhn.domain.user.User;
import com.redhat.rhn.frontend.listview.ListControl;
import com.redhat.rhn.frontend.struts.RequestContext;
import com.redhat.rhn.manager.channel.ChannelManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ChannelTreeRelevantSetupAction
 * @version $Rev$
 */
public class PopularChannelTreeAction extends BaseChannelTreeAction {

    private Long count;
    private Long defaultCount = 10L;
    
    private final Long[] preSetCounts = {1L, 10L, 50L, 100L, 250L, 500L, 1000L};
    
    private String SERVER_COUNT = "server_count";
    private String COUNTS = "counts";
    
    /** {@inheritDoc} */
    public ActionForward execute(ActionMapping mapping,
            ActionForm formIn,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        RequestContext requestContext = new RequestContext(request);

        User user = requestContext.getLoggedInUser();
        String countStr = request.getParameter(SERVER_COUNT);
        if (countStr == null) {
            count = defaultCount;
            /**
            Long sysPercent = new Long(UserManager.visibleSystemsAsDto(user).size()/10);
            
            for (Long l : preSetCounts) {
                if (l.longValue() < sysPercent.longValue()) {
                    count = l;
                }
            }
            if (count == null) {
                count = 500L;
            } **/
        }
        else {
            count = Long.parseLong(countStr);
        }
        
        List<Map> preSetList = new ArrayList<Map>();
        for (Long l : preSetCounts) {
            Map countMap = new HashMap();
            countMap.put("count", l);
            countMap.put("selected", l.equals(count));            
            preSetList.add(countMap);
        }
        
        
        request.setAttribute(COUNTS, preSetList);
        request.setAttribute(SERVER_COUNT, count);
        return super.execute(mapping, formIn, request, response);
    }
            
            
    
    
    /** {@inheritDoc} */
    protected DataResult getDataResult(User user, ListControl lc) {
        DataResult dr = ChannelManager.popularChannelTree(user, count, lc);
        return  dr;
    }
}
