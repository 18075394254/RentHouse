package myinterface;

import java.util.List;

/**
 * Created by user on 2019/8/9.
 */

public interface PermissionListener {
    void granted();
    void denied(List<String> deniedList);
}
