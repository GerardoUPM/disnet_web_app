package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by gerardo on 16/11/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_rest
 * @className Source
 * @see
 */
public class Source {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String name;
    private int snapshotCount;
    private List<Snapshot> snapshots;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSnapshotCount() {
        return snapshotCount;
    }

    public void setSnapshotCount(int snapshotCount) {
        this.snapshotCount = snapshotCount;
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }


    @Override
    public String toString() {
        return "    Source{" + '\n' +
                "       id='" + id + '\n' +
                "       , name='" + name + '\n' +
                "       , snapshots=" + snapshots + '\n' +
                "   }";
    }
}
