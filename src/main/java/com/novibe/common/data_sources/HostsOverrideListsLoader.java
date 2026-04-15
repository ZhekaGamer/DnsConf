package com.novibe.common.data_sources;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class HostsOverrideListsLoader extends ListLoader<HostsOverrideListsLoader.BypassRoute> {

    public record BypassRoute(String ip, String website) {
    }

    @Override
    protected String listType() {
        return "Override";
    }

    @Override
    protected Predicate<String> filterRelatedLines() {
        return line -> !HostsBlockListsLoader.isBlock(line);
    }

    @Override
    protected BypassRoute toObject(String line) {
        int delimiter = line.indexOf(" ");
        
        if (delimiter == -1) {
            return null; 
        }

        String ip = line.substring(0, delimiter).strip();
        String website = removeWWW(line.substring(delimiter).strip());
        
        if (ip.isEmpty() || website.isEmpty()) {
            return null;
        }

        return new BypassRoute(ip, website);
    }

}
