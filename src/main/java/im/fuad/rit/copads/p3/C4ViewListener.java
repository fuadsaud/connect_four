package im.fuad.rit.copads.p3;

import java.io.IOException;

/**
 * Defines the operations necessary for objects that want to listen to view events.
 *
 * @author Fuad Saud <ffs3415@rit.edu>
 */
public interface C4ViewListener {
    public void addMarker(Integer col) throws IOException;
    public void clear() throws IOException;
}
