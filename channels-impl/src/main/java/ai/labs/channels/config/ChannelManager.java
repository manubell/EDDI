package ai.labs.channels.config;

import ai.labs.channels.config.model.ChannelDefinition;
import ai.labs.channels.differ.IDifferEndpoint;
import ai.labs.channels.xmpp.IXmppEndpoint;
import ai.labs.runtime.DependencyInjector;

import static ai.labs.channels.differ.IDifferEndpoint.RESOURCE_URI_DIFFER_CHANNEL_CONNECTOR;
import static ai.labs.channels.xmpp.IXmppEndpoint.RESOURCE_URI_XMPP_CHANNEL_CONNECTOR;

public class ChannelManager implements IChannelManager {

    private final DependencyInjector injector;

    public ChannelManager() {
        this.injector = DependencyInjector.getInstance();
    }

    @Override
    public void initChannel(ChannelDefinition channelDefinition) {
        String channelType = channelDefinition.getType().toString();
        if (!channelDefinition.isActive()) {
            return;
        }

        switch (channelType) {
            case RESOURCE_URI_XMPP_CHANNEL_CONNECTOR:
                injector.getInstance(IXmppEndpoint.class).init(channelDefinition);
                break;

            case RESOURCE_URI_DIFFER_CHANNEL_CONNECTOR:
                injector.getInstance(IDifferEndpoint.class).init(channelDefinition);
                break;
        }
    }
}
