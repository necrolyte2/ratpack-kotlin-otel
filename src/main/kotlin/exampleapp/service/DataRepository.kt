package exampleapp.service

import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.google.inject.Inject
import exampleapp.mapper.DataRowMapper
import exampleapp.mapper.datarow.DataRow
import ratpack.exec.Promise
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent
import ratpackkotlinotel.promisesession.PromiseSession
import java.util.concurrent.CompletionStage

class DataRepository @Inject constructor(
    private val promiseSession: PromiseSession
): Service {
    override fun onStart(event: StartEvent) {
        promiseSession.execute(
            SimpleStatement.newInstance(
        "CREATE KEYSPACE IF NOT EXISTS datarepo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}"
            )
        )
        promiseSession.execute("USE datarepo")
        promiseSession.execute(
            SimpleStatement.newInstance(
        "CREATE TABLE IF NOT EXISTS datarow (" +
                "  id text," +
                "  value text," +
                "  PRIMARY KEY (id)" +
                ");"
            )
        )
        promiseSession.execute(
            SimpleStatement.newInstance(
                "INSERT INTO datarow (id, value) VALUES ('1', 'testing') IF NOT EXISTS"
            )
        )
    }

    override fun onStop(event: StopEvent) {
        promiseSession.close()
    }

    fun toDataRowPromise(completionStage: CompletionStage<DataRow>): Promise<DataRow> {
        return Promise.async<DataRow> { upstream ->
            upstream.accept(completionStage)
        }
    }

    fun get(id: String): Promise<DataRow> {
        val mapper: DataRowMapper = DataRowMapper.builder(promiseSession).withDefaultKeyspace("datarepo").build()
        val dao = mapper.dataRowDao()
        return PromiseSession.toPromise(dao.get(id))
    }
}