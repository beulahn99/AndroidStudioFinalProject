/**
 *  Repository class for handling word data.
 *  Author: Jeel Mitesh Tandel
 *  Lab Section: CST2335 013
 *  Creation Date: March 25, 2024
 */
package algonquin.cst2335.tand0019.dictionaryapp.data.repository;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import java.util.List;

import algonquin.cst2335.tand0019.dictionaryapp.data.local.AppDatabase;
import algonquin.cst2335.tand0019.dictionaryapp.data.local.WordsDao;
import algonquin.cst2335.tand0019.dictionaryapp.data.model.wordsapi.Word;
import algonquin.cst2335.tand0019.dictionaryapp.data.remote.wordsapi.WordsApiService;
import algonquin.cst2335.tand0019.dictionaryapp.ui.main.DictionaryMainActivity;
import algonquin.cst2335.tand0019.dictionaryapp.util.WordResponseListener;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordsRepo {
    private WordsDao mWordsDao;
    private LiveData<List<Word>> mFavWords;
    private static WordsRepo instance;
    private static final String TAG = "WordsRepo";
    private Word word;
    private WordsApiService wordsApiService = new WordsApiService();

    /**
     * Gets an instance of the WordsRepo class.
     * @param app The application context
     * @return An instance of WordsRepo
     */
    public static WordsRepo getInstance(Application app){
        if (instance == null){
            instance = new WordsRepo(app);
        }
        return instance;
    }

    /**
     * Constructor to initialize the WordsRepo.
     * @param app The application context
     */
    private WordsRepo(Application app){
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        this.mWordsDao = appDatabase.wordsDao();
        this.mFavWords = mWordsDao.getAllFavouriteWords();
    }

    /**
     * Retrieves live data of favorite words.
     * @return LiveData object containing list of favorite words
     */
    public LiveData<List<Word>> getFavWords(){
        return this.mFavWords;
    }

    /**
     * Finds if a word exists in the list of favorite words.
     * @param w The word to search for
     * @return A Single object containing the found word
     */
    public Single<Word> findWord(String w){
        return this.mWordsDao.findWord(w);
    }

    /**
     * Retrieves word details from the API.
     * @param w The word to retrieve details for
     * @param resultListener The listener for API response
     */
    public void getWordFromApi(String w, WordResponseListener resultListener){
        Call<Word> wordResponse =  wordsApiService.getWord(w);
        word = null;
        wordResponse.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"Code " + response.code());
                    resultListener.onFail("Code " + response.code());
                } else {
                    word = response.body();
                    resultListener.onSuccess(word);
                }
            }
            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                Log.d(TAG,"onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Inserts a word into the database.
     * @param w The word to insert
     */
    public void insertWord(Word w){
        new insertAsyncTask(mWordsDao).execute(w);
    }

    /**
     * Removes a word from the database.
     * @param w The word to remove
     */
    public void removeWord(Word w){
        new removeAsyncTask(mWordsDao).execute(w);
    }

    /**
     * Removes a list of words from the database.
     * @param w The list of words to remove
     * @param mListener The listener for removing selected words
     */
    public void removeListofWords(List<Word> w, DictionaryMainActivity.OnRemoveSelectedWords mListener){
        new removeListAsyncTask(mListener, mWordsDao).execute(w);
    }

    /**
     * AsyncTask to insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordsDao mAsyncTaskDao;
        insertAsyncTask(WordsDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Word... params) {
            mAsyncTaskDao.insertWord(params[0]);
            return null;
        }
    }

    /**
     * AsyncTask to remove a word from the database.
     */
    private static class removeAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordsDao mAsyncTaskDao;
        removeAsyncTask(WordsDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.removeWord(params[0]);
            return null;
        }
    }

    /**
     * AsyncTask to remove a list of words from the database.
     */
    private static class removeListAsyncTask extends AsyncTask<List<Word>, Void, Boolean>{
        private final DictionaryMainActivity.OnRemoveSelectedWords mListener;
        private final WordsDao mAsyncTaskDao;
        removeListAsyncTask(DictionaryMainActivity.OnRemoveSelectedWords mListener, WordsDao dao) {
            this.mListener = mListener;
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(List<Word>... lists) {
            mAsyncTaskDao.removeListofWords(lists[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mListener!=null){
                mListener.clear(aBoolean);
            }
        }
    }
}
