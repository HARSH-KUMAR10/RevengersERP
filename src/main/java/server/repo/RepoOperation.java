package server.repo;

public interface RepoOperation<Req,Res>
{
    Res post(Req params);

    Res get(Req params);

    Res put(Req params);

    Res delete(Req params);
}
