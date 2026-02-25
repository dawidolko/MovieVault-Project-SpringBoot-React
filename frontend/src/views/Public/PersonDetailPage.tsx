import { useEffect, useState } from "react";
import { useParams, Link } from "react-router";
import { api } from "../../services/api";

const PersonDetailPage = () => {
  const { id } = useParams();
  const [data, setData] = useState<any>(null);

  useEffect(() => {
    api.get(`/persons/${id}`).then((r) => setData(r.data)).catch(() => {});
  }, [id]);

  if (!data) return <div className="page-wrapper"><div className="container-xl py-4">Loading...</div></div>;

  const { person, actedIn, directed } = data;

  return (
    <div className="page-wrapper">
      <div className="page-body">
        <div className="container-xl">
          <h2 className="page-title mb-4">{person.firstName} {person.lastName}</h2>
          <div className="row">
            <div className="col-lg-8">
              {person.bio && <div className="card mb-3"><div className="card-body"><p>{person.bio}</p></div></div>}

              {directed?.length > 0 && (
                <div className="card mb-3">
                  <div className="card-header"><h3 className="card-title">Directed</h3></div>
                  <div className="list-group list-group-flush">
                    {directed.map((m: any) => (
                      <Link key={m.id} to={`/movies/${m.id}`} className="list-group-item list-group-item-action">
                        {m.title} ({m.releaseDate?.substring(0, 4)})
                      </Link>
                    ))}
                  </div>
                </div>
              )}

              {actedIn?.length > 0 && (
                <div className="card mb-3">
                  <div className="card-header"><h3 className="card-title">Acted In</h3></div>
                  <div className="list-group list-group-flush">
                    {actedIn.map((m: any) => (
                      <Link key={m.id} to={`/movies/${m.id}`} className="list-group-item list-group-item-action">
                        {m.title} ({m.releaseDate?.substring(0, 4)})
                      </Link>
                    ))}
                  </div>
                </div>
              )}
            </div>
            <div className="col-lg-4">
              <div className="card">
                <div className="card-body">
                  <div className="datagrid">
                    {person.birthDate && <div className="datagrid-item"><div className="datagrid-title">Born</div><div className="datagrid-content">{person.birthDate}</div></div>}
                    {person.birthPlace && <div className="datagrid-item"><div className="datagrid-title">Birthplace</div><div className="datagrid-content">{person.birthPlace}</div></div>}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PersonDetailPage;
