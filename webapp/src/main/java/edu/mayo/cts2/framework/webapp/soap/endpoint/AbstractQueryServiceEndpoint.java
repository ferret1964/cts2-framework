package edu.mayo.cts2.framework.webapp.soap.endpoint;

import edu.mayo.cts2.framework.model.core.Directory;
import edu.mayo.cts2.framework.model.core.RESTResource;
import edu.mayo.cts2.framework.model.core.types.CompleteDirectory;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.service.core.QueryControl;
import edu.mayo.cts2.framework.model.service.core.ReadContext;
import edu.mayo.cts2.framework.service.profile.QueryService;
import edu.mayo.cts2.framework.webapp.soap.directoryuri.SoapDirectoryUriRequest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;


public abstract class AbstractQueryServiceEndpoint extends AbstractEndpoint {

  protected <T, I> T doCount(
      final QueryService queryService,
      final I identifier,
      final QueryControl queryControl,
      ReadContext readContext) {
    return null;
  }

  protected <T, I> T doUnion(
      final QueryService queryService,
      final I identifier,
      final QueryControl queryControl,
      ReadContext readContext) {
    return null;
  }

  protected <T, I> T doIntersect(
      final QueryService queryService,
      final I identifier,
      final QueryControl queryControl,
      ReadContext readContext) {
    return null;
  }

  protected <T, I> T doDifference(
      final QueryService queryService,
      final I identifier,
      final QueryControl queryControl,
      ReadContext readContext) {
    return null;
  }

  protected <T, I> T doRestrict(
      final QueryService queryService,
      final I identifier,
      final QueryControl queryControl,
      ReadContext readContext) {
    return null;
  }

  protected <T extends Directory> T populateDirectory(
      DirectoryResult<?> result,
      SoapDirectoryUriRequest<?> request,
      Class<T> directoryClazz) {

    boolean atEnd = result.isAtEnd();

    boolean isComplete = atEnd && (request.getPage() == 0);

    T directory;
    try {
      directory = directoryClazz.newInstance();

      final Field field = ReflectionUtils.findField(directoryClazz,
          "_entryList");

      AccessController.doPrivileged(new PrivilegedAction<Void>() {
        public Void run() {
          field.setAccessible(true);

          return null;
        }
      });

      ReflectionUtils.setField(field, directory, result.getEntries());
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }

    if (isComplete) {
      directory.setComplete(CompleteDirectory.COMPLETE);
    }
    else {
      directory.setComplete(CompleteDirectory.PARTIAL);

      //TODO:
    }

    directory.setNumEntries((long) result.getEntries().size());

    directory.setHeading(new RESTResource());
    directory.getHeading().setAccessDate(new Date());
    directory.getHeading().setResourceRoot("soap");
    directory.getHeading().setResourceURI("soap");

    return directory;
  }
}
