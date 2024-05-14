import { albumApi } from "../apiService";

const token = JSON.parse(localStorage.getItem('@Auth.Token') || '{}');
albumApi.defaults.headers.common.Authorization = `Basic ${token}`;

const getAlbums = async (search: string) => {
  try {
    const resp = await albumApi.get(`/albums/all?searchText=${search}`)
    return resp.data;
  } catch (error) {
    throw new Error('Erro ao buscar os álbuns');
  }

}

const getUserAlbums = async () => {
    try {
      const resp = await albumApi.get(`/albums/my-collection`)
      return resp.data;
    } catch (error) {
      throw new Error('Erro ao buscar os álbuns');
    }  
}

export {getAlbums, getUserAlbums};