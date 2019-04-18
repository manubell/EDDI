package ai.labs.resources.impl.behavior.rest;

import ai.labs.persistence.IResourceStore;
import ai.labs.resources.impl.resources.rest.RestVersionInfo;
import ai.labs.resources.rest.behavior.IBehaviorStore;
import ai.labs.resources.rest.behavior.IRestBehaviorStore;
import ai.labs.resources.rest.behavior.model.BehaviorConfiguration;
import ai.labs.resources.rest.documentdescriptor.IDocumentDescriptorStore;
import ai.labs.resources.rest.documentdescriptor.model.DocumentDescriptor;
import ai.labs.rest.restinterfaces.IRestInterfaceFactory;
import ai.labs.rest.restinterfaces.RestInterfaceFactory;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author ginccc
 */
@Slf4j
public class RestBehaviorStore extends RestVersionInfo<BehaviorConfiguration> implements IRestBehaviorStore {
    private final IBehaviorStore behaviorStore;
    private IRestBehaviorStore restBehaviorStore;

    @Inject
    public RestBehaviorStore(IBehaviorStore behaviorStore,
                             IRestInterfaceFactory restInterfaceFactory,
                             IDocumentDescriptorStore documentDescriptorStore) {
        super(resourceURI, behaviorStore, documentDescriptorStore);
        this.behaviorStore = behaviorStore;
        initRestClient(restInterfaceFactory);
    }

    private void initRestClient(IRestInterfaceFactory restInterfaceFactory) {
        try {
            restBehaviorStore = restInterfaceFactory.get(IRestBehaviorStore.class);
        } catch (RestInterfaceFactory.RestInterfaceFactoryException e) {
            restBehaviorStore = null;
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public List<DocumentDescriptor> readBehaviorDescriptors(String filter, Integer index, Integer limit) {
        return readDescriptors("ai.labs.behavior", filter, index, limit);
    }

    @Override
    public BehaviorConfiguration readBehaviorRuleSet(String id, Integer version) {
        return read(id, version);
    }

    @Override
    public Response updateBehaviorRuleSet(String id, Integer version, BehaviorConfiguration behaviorConfiguration) {
        return update(id, version, behaviorConfiguration);
    }

    @Override
    public Response createBehaviorRuleSet(BehaviorConfiguration behaviorConfiguration) {
        return create(behaviorConfiguration);
    }

    @Override
    public Response deleteBehaviorRuleSet(String id, Integer version) {
        return delete(id, version);
    }

    @Override
    public Response duplicateBehaviorRuleSet(String id, Integer version) {
        validateParameters(id, version);
        var behaviorConfiguration = restBehaviorStore.readBehaviorRuleSet(id, version);
        return restBehaviorStore.createBehaviorRuleSet(behaviorConfiguration);
    }

    @Override
    protected IResourceStore.IResourceId getCurrentResourceId(String id) throws IResourceStore.ResourceNotFoundException {
        return behaviorStore.getCurrentResourceId(id);
    }
}
