package cn.haoke.mgmt.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

public class UrlWhiteSet {

    private Set<String> uriSet;


    public boolean contains(String uri) {
        boolean bool = false;
        AntPathMatcher ant;
        if (this.uriSet != null) {
            ant = new AntPathMatcher();
            for (String u : this.uriSet) {
                bool = ant.match(u, uri);
                if (bool) break;
            }
        }
        return bool;
    }

    public Set<String> getUriSet() {
        return uriSet;
    }

    public void setUriSet(Set<String> uriSet) {
        this.uriSet = uriSet;
    }
}
